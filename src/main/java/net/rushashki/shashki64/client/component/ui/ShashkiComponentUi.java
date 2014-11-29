package net.rushashki.shashki64.client.component.ui;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.shared.model.dto.PlayDto;
import net.rushashki.shashki64.shashki.BoardBackgroundLayer;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 29.11.14
 * Time: 0:38
 */
public class ShashkiComponentUi extends Composite implements ClickHandler {
  private static ShashkiUiBinder ourUiBinder = GWT.create(ShashkiUiBinder.class);
  private final LienzoPanel lienzoPanel;
  private final ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  @UiField
  HTMLPanel shashki;
  @UiField
  HTMLPanel social;
  @UiField
  HTMLPanel comments;
  private static ShashkiComponentUi INSTANCE;
  private PlayDto playDto;

  public ShashkiComponentUi(PlayDto playDto) {
    initWidget(ourUiBinder.createAndBindUi(this));

    this.playDto = playDto;
    INSTANCE = this;

    int side = Window.getClientHeight() - RootPanel.get("navigation").getOffsetHeight() - RootPanel.get("footer").getOffsetHeight();
    lienzoPanel = new LienzoPanel(side, side);
    BoardBackgroundLayer boardBackgroundLayer = new BoardBackgroundLayer(
        lienzoPanel.getHeight(), lienzoPanel.getHeight() - 20,
        8, 8);
    lienzoPanel.setBackgroundLayer(boardBackgroundLayer);
    shashki.add(lienzoPanel);

    social.add(new HTML("Твитнуть"));
    comments.add(new HTML("Отличная игра!"));

    lienzoPanel.addClickHandler(this);
  }

  public void update() {
    lienzoPanel.getScene().draw();
    Layer layer = new Layer();
    Text text = new Text("Hi");
    text.setX(0).setY(10);
    layer.add(text);
    lienzoPanel.add(layer);
  }

  @Override
  public void onClick(ClickEvent clickEvent) {
    INSTANCE.removeStyleName("focused");
    addStyleName("focused");
    RootPanel.get().getElement().setScrollTop(this.getElement().getAbsoluteTop() - 50);
    INSTANCE = this;
  }

  interface ShashkiUiBinder extends UiBinder<HTMLPanel, ShashkiComponentUi> {
  }
}