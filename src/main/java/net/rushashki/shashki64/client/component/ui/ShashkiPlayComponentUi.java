package net.rushashki.shashki64.client.component.ui;

import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import net.rushashki.shashki64.client.component.widget.NotationPanel;
import net.rushashki.shashki64.client.event.OnGetProfileEvent;
import net.rushashki.shashki64.shared.model.Shashist;
import net.rushashki.shashki64.shashki.Board;
import net.rushashki.shashki64.shashki.BoardBackgroundLayer;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 13:12
 */
public class ShashkiPlayComponentUi extends BasicComponent {
  private static ShashkiPlayComponentUiUiBinder ourUiBinder = GWT.create(ShashkiPlayComponentUiUiBinder.class);
  private final Board board;

  @UiField
  HTMLPanel shashki;
  @UiField
  HTMLPanel shashkiColumn;
  @UiField
  HTMLPanel privateChatColumn;
  @UiField
  HTMLPanel notationColumn;
  @UiField
  HTMLPanel notationList;
  @UiField
  HTMLPanel playerListColumn;

  private final LienzoPanel lienzoPanel;
  private Shashist player;

  public ShashkiPlayComponentUi() {
    initWidget(ourUiBinder.createAndBindUi(this));

    int shashkiSide = Window.getClientHeight() - RootPanel.get("navigation").getOffsetHeight() -
        RootPanel.get("footer").getOffsetHeight();
    shashkiColumn.setWidth(shashkiSide + "px");

    lienzoPanel = new LienzoPanel(shashkiSide, shashkiSide);
    int lienzoSide = lienzoPanel.getHeight() - 20;
    BoardBackgroundLayer boardBackgroundLayer = new BoardBackgroundLayer(
        lienzoSide, lienzoSide - 30,
        8, 8);
    lienzoPanel.setBackgroundLayer(boardBackgroundLayer);
    shashki.add(lienzoPanel);

    board = new Board(boardBackgroundLayer, 8, 8, true);
    lienzoPanel.add(board);

    NotationPanel notationPanel = new NotationPanel();
    notationList.add(notationPanel);

    Scheduler.get().scheduleFinally(() -> {
      // 81 - отступы
      int side = Window.getClientWidth() - shashkiColumn.getOffsetWidth() - notationColumn.getOffsetWidth()
          - playerListColumn.getOffsetWidth() - 81;
      privateChatColumn.setWidth(side + "px");
      String notationHeight = lienzoPanel.getHeight() - 50 + "px";
      notationPanel.setHeight(notationHeight);
    });

    eventBus.addHandler(OnGetProfileEvent.TYPE, event -> {
      player = event.getProfile();
    });
  }

  interface ShashkiPlayComponentUiUiBinder extends UiBinder<HTMLPanel, ShashkiPlayComponentUi> {
  }
}