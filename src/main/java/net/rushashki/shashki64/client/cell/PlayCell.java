package net.rushashki.shashki64.client.cell;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiRenderer;
import net.rushashki.shashki64.client.cell.renderer.PlayCellDtoRenderer;
import net.rushashki.shashki64.shared.model.dto.PlayDto;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 25.11.14
 * Time: 16:55
 */
public class PlayCell extends AbstractCell<PlayCellDtoRenderer> {

  private static final PlayCellBinder cellRenderer = GWT.create(PlayCellBinder.class);

  public interface PlayCellStyle extends CssResource {
    String applyColor();
  }

  interface PlayCellBinder extends UiRenderer {
    void render(SafeHtmlBuilder sb, PlayCellDtoRenderer myCellRenderer);

    void onBrowserEvent(PlayCell cell, NativeEvent e, Element p, PlayDto myDTO);

    //get the div element
//    DivElement getPlayCell(Element parent);

    PlayCellStyle getStyle();
  }

  @Override
  public void render(Context context, PlayCellDtoRenderer value, SafeHtmlBuilder sb) {
    if (value == null) {
      return;
    }
//    PlayCellDtoRenderer myCellRenderer = new PlayCellDtoRenderer((PlayDto) value);
    cellRenderer.render(sb, value);
  }

//  @Override
//  public void onBrowserEvent(Context context, Element parent, PlayDto value, NativeEvent event, ValueUpdater updater) {
//    cellRenderer.onBrowserEvent(this, event, parent, value);
//  }

//  @UiHandler({"playCell"})
//  public void onCellSelected(ClickEvent event, Element parent, PlayDto myDTO) {
//    Element cell = cellRenderer.getPlayCell(parent);
//
//    // apply this style if this cell is clicked
//    cell.addClassName(cellRenderer.getStyle().applyColor());
//  }

/*
  private static PlayCellUiRenderer ourUiRenderer = GWT.create(PlayCellUiRenderer.class);

  @Override
  public void render(Context context, String value, SafeHtmlBuilder sb) {
    ourUiRenderer.render(sb, value);
  }

  interface PlayCellUiRenderer extends UiRenderer {
    void render(SafeHtmlBuilder safeHtmlBuilder, String name);

    void onBrowserEvent( PlayCell cell, NativeEvent e, Element p, MyDTO myDTO );
  }
*/

}