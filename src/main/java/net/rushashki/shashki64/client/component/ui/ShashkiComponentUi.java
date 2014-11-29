package net.rushashki.shashki64.client.component.ui;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.core.types.Shadow;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.shared.core.types.ColorName;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.client.util.ShashkiLogger;
import net.rushashki.shashki64.shared.model.dto.PlayDto;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 29.11.14
 * Time: 0:38
 */
public class ShashkiComponentUi extends Composite {
  private static ShashkiUiBinder ourUiBinder = GWT.create(ShashkiUiBinder.class);
  private final LienzoPanel lienzoPanel;
  private final ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  private final ShashkiLogger logger;
  @UiField
  HTMLPanel shashki;
  private PlayDto playDto;

  public ShashkiComponentUi(PlayDto playDto) {
    initWidget(ourUiBinder.createAndBindUi(this));

    this.playDto = playDto;
    this.logger = shashkiGinjector.getLogger();

    Text text = new Text(playDto.getTitle() + " white:" + playDto.getWhitePlayer() + " black:" + playDto.getBlackPlayer(),
        "Verdana, sans-serif", "italic bold", 12);
    text.setX(0).setY(25);
    text.setFillColor(ColorName.CORNFLOWERBLUE);
    text.setStrokeColor(ColorName.BLUE);
    text.setShadow(new Shadow(ColorName.DARKMAGENTA, 6, 4, 4));

    lienzoPanel = new LienzoPanel(100, 50);

    Layer layer = new Layer();
    layer.add(text);

    lienzoPanel.setBackgroundLayer(layer);

    shashki.add(lienzoPanel);
  }

  public void update() {
    lienzoPanel.getScene().draw();
    Layer layer = new Layer();
    Text text = new Text("Hi");
    text.setX(0).setY(10);
    layer.add(text);
    lienzoPanel.add(layer);
  }

  interface ShashkiUiBinder extends UiBinder<HTMLPanel, ShashkiComponentUi> {
  }
}