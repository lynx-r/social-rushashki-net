package net.rushashki.shashki64.client.component.ui;

import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.shashki64.client.cell.PlayCell;
import net.rushashki.shashki64.client.cell.proxy.PlayCellProxy;
import net.rushashki.shashki64.client.util.UpdateLienzo;
import net.rushashki.shashki64.shared.model.dto.PlayDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 25.11.14
 * Time: 9:18
 */
public class ShashkiListComponentUi extends Composite {

  interface ShashkiListComponentUiUiBinder extends UiBinder<HTMLPanel, ShashkiListComponentUi> {
  }

  private static ShashkiListComponentUiUiBinder ourUiBinder = GWT.create(ShashkiListComponentUiUiBinder.class);

  @UiField
  HTMLPanel shashkiList;

  public ShashkiListComponentUi() {
    initWidget(ourUiBinder.createAndBindUi(this));

    List<ShashkiComponentUi> plays = new ArrayList<>();
    List<PlayCellProxy> playCellProxies = new ArrayList<>();

    PlayDto playDto;
    for (int i = 0; i < 10; i++) {
      playDto = new PlayDto("test" + i, "master" + i, "bachelor" + i);
      ShashkiComponentUi shashkiComponentUi = new ShashkiComponentUi(playDto);
      plays.add(shashkiComponentUi);
      playDto.setDeskHtml(shashkiComponentUi::toString);
      playDto.setUpdateCanvas(shashkiComponentUi::update);
      playCellProxies.add(new PlayCellProxy(playDto));
    }

    CellList<PlayCellProxy> cellProxyCellList = new CellList<>(new PlayCell());
    cellProxyCellList.setRowData(playCellProxies);

    shashkiList.add(cellProxyCellList);

//    for (ShashkiComponentUi play : plays) {
//      shashkiList.add(play);
//    }
  }

}