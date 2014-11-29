package net.rushashki.shashki64.client.cell;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiRenderer;
import net.rushashki.shashki64.client.cell.proxy.PlayCellProxy;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 25.11.14
 * Time: 16:55
 */
public class PlayCell extends AbstractCell<PlayCellProxy> {

  private static final PlayCellBinder cellRenderer = GWT.create(PlayCellBinder.class);

  @UiField
  DivElement play;

  @Override
  public void render(Context context, PlayCellProxy value, SafeHtmlBuilder sb) {
    if (value == null) {
      return;
    }

    cellRenderer.render(sb, value);
    value.updateCanvas();
  }

  interface PlayCellBinder extends UiRenderer {
    void render(SafeHtmlBuilder sb, PlayCellProxy playCellProxy);
  }

}