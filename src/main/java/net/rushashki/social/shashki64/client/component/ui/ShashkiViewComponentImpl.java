package net.rushashki.social.shashki64.client.component.ui;

import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import net.rushashki.social.shashki64.client.event.ClientFactoryEvent;
import net.rushashki.social.shashki64.client.event.ClientFactoryEventHandler;
import net.rushashki.social.shashki64.shared.model.Shashist;
import net.rushashki.social.shashki64.shared.dto.PlayDto;
import net.rushashki.social.shashki64.shashki.BoardBackgroundLayer;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 29.11.14
 * Time: 0:38
 */
public class ShashkiViewComponentImpl extends BasicComponent implements ClickHandler {
  private static Binder ourUiBinder = GWT.create(Binder.class);
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

  private static ShashkiViewComponentImpl INSTANCE;
  private PlayDto playDto;

  public ShashkiViewComponentImpl(PlayDto playDto) {
    initWidget(ourUiBinder.createAndBindUi(this));

    this.playDto = playDto;
    INSTANCE = this;

    int shashkiSide = Window.getClientHeight() - RootPanel.get("navigation").getOffsetHeight() -
        RootPanel.get("footer").getOffsetHeight();
    shashkiColumn.setWidth(shashkiSide + "px");

    lienzoPanel = new LienzoPanel(shashkiSide, shashkiSide);
    BoardBackgroundLayer boardBackgroundLayer = new BoardBackgroundLayer(
        lienzoPanel.getHeight(), lienzoPanel.getHeight() - 30,
        8, 8);
    lienzoPanel.setBackgroundLayer(boardBackgroundLayer);
    shashki.add(lienzoPanel);

    social.add(new HTML("Твитнуть"));
    comments.add(new HTML("Отличная игра!"));

    lienzoPanel.addClickHandler(this);

    // TODO: Not Compile
    eventBus.addHandler(ClientFactoryEvent.TYPE, new ClientFactoryEventHandler() {
      @Override
      public void onClientFactory(ClientFactoryEvent event) {
        ShashkiViewComponentImpl.this.player = event.getClientFactory().getPlayer();
        alignNotationPanel(shashkiSide);
      }
    });

    if (player == null) {
      Scheduler.get().scheduleFinally(() -> {
          alignNotationPanel(shashkiSide);
      });
    }
  }

  private void alignNotationPanel(int shashkiSide) {
    if (this.getOffsetWidth() > 0) {
      sidebarColumn.setWidth(this.getOffsetWidth() - shashkiSide + "px");
      sidebarColumn.getElement().getStyle().setMarginLeft(shashkiSide + 15, Style.Unit.PX);
      // 20 текст Нотация
      notationList.setHeight(shashkiColumn.getOffsetHeight() - 20 + "px");
    }
  }

  @Override
  public void onClick(ClickEvent clickEvent){
    INSTANCE.removeStyleName("focused");
    addStyleName("focused");
    RootPanel.get().getElement().setScrollTop(this.getElement().getAbsoluteTop() - 50);
    INSTANCE = this;
  }

  interface Binder extends UiBinder<HTMLPanel, ShashkiViewComponentImpl> {
  }
}