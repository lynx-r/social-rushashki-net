package net.rushashki.social.shashki64.client.component.ui;

import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import net.rushashki.social.shashki64.client.component.widget.NotationPanel;
import net.rushashki.social.shashki64.client.util.Utils;
import net.rushashki.social.shashki64.shared.dto.PlayDto;
import net.rushashki.social.shashki64.shared.model.Shashist;
import net.rushashki.social.shashki64.shashki.Board;
import net.rushashki.social.shashki64.shashki.BoardBackgroundLayer;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 29.11.14
 * Time: 0:38
 */
public class ShashkiViewComponentImpl extends BasicComponent implements ClickHandler {
  private static Binder ourUiBinder = GWT.create(Binder.class);
  private Board board;
  private LienzoPanel lienzoPanel;
  private Shashist player;

  @UiField
  HTMLPanel shashki;
  @UiField
  HTMLPanel social;
  @UiField
  HTMLPanel comments;
  @UiField
  HTMLPanel shashkiColumn;
  @UiField
  HTMLPanel sidebarColumn;
  @UiField
  ScrollPanel notationList;
  @UiField
  HTML title;

  private NotationPanel notationPanel;

  private static ShashkiViewComponentImpl INSTANCE;
  private PlayDto playDto;

  public ShashkiViewComponentImpl(PlayDto playDto) {
    initWidget(ourUiBinder.createAndBindUi(this));

    String winner = Utils.getGameEnd(playDto.getGameEnd(), constants);
    title.setHTML(constants.playViewTitle(playDto.getWhitePlayer(), playDto.getBlackPlayer(), winner));

    title.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        lienzoPanel.setVisible(!lienzoPanel.isVisible());
        sidebarColumn.setVisible(!sidebarColumn.isVisible());
      }
    });

    this.playDto = playDto;
    INSTANCE = this;

    initEmptyDeskPanel(constants.playStartDescription());
    initNotationPanel();
    lienzoPanel.setVisible(false);
    sidebarColumn.setVisible(false);
  }

  private BoardBackgroundLayer initDeskPanel(boolean white) {
    int lienzoSide = lienzoPanel.getHeight() - 50;
    BoardBackgroundLayer boardBackgroundLayer = new BoardBackgroundLayer(
        lienzoSide, lienzoSide - 30,
        8, 8);
    boardBackgroundLayer.drawCoordinates(white);
    lienzoPanel.setBackgroundLayer(boardBackgroundLayer);
    return boardBackgroundLayer;
  }

  private void initNotationPanel() {
    notationPanel = new NotationPanel();
    notationList.add(notationPanel);

    Scheduler.get().scheduleFinally(this::alignNotationPanel);
  }

  private void alignNotationPanel() {
    if (Window.getClientWidth() > 0) {
      // 81 - отступы
//      int side = Window.getClientWidth() - shashkiColumn.getOffsetWidth() - notationColumn.getOffsetWidth()
//          - playerListColumn.getOffsetWidth() - 81;
//      privateChatColumn.setWidth(side + "px");
      String notationHeight = lienzoPanel.getHeight() - 170 + "px";
      notationPanel.setHeight(notationHeight);
    }
  }

  private void initEmptyDeskPanel(String playStartDescription) {
    int shashkiSide = Window.getClientHeight() - RootPanel.get("navigation").getOffsetHeight() -
        RootPanel.get("footer").getOffsetHeight();
    shashkiColumn.setWidth(shashkiSide + "px");

    lienzoPanel = new LienzoPanel(shashkiSide, shashkiSide);
//    int lienzoSide = lienzoPanel.getHeight() - 20;
//    Layer initDeskRect = new Layer();
//    Rectangle contour = new Rectangle(lienzoSide, lienzoSide);
//    contour.setX(1);
//    contour.setY(1);
//    initDeskRect.add(contour);
//    String[] descriptions = playStartDescription.split("\n");
//    int y = 0;
//    for (String description : descriptions) {
//      Text greeting = new Text(description, "Times New Roman", 14);
//      greeting.setFillColor("blue");
//      greeting.setStrokeColor("blue");
//      greeting.setY(lienzoSide / 2 - 20 + y);
//      greeting.setX(lienzoSide / 2 - 180);
//      initDeskRect.add(greeting);
//      y += 20;
//    }
//    lienzoPanel.setBackgroundLayer(initDeskRect);
    shashki.add(lienzoPanel);

    boolean isWhite = true;
    BoardBackgroundLayer backgroundLayer = initDeskPanel(isWhite);
    board = new Board(backgroundLayer, 8, 8, isWhite);
    lienzoPanel.add(board);
  }

//  private void alignNotationPanel(int shashkiSide) {
//    if (this.getOffsetWidth() > 0) {
//      sidebarColumn.setWidth(this.getOffsetWidth() - shashkiSide + "px");
//      sidebarColumn.getElement().getStyle().setMarginLeft(shashkiSide + 15, Style.Unit.PX);
//      // 20 текст Нотация
//      notationList.setHeight(shashkiColumn.getOffsetHeight() - 20 + "px");
//    }
//  }

  @Override
  public void onClick(ClickEvent clickEvent) {
    INSTANCE.removeStyleName("focused");
    addStyleName("focused");
    RootPanel.get().getElement().setScrollTop(this.getElement().getAbsoluteTop() - 50);
    INSTANCE = this;
  }

  interface Binder extends UiBinder<HTMLPanel, ShashkiViewComponentImpl> {
  }
}