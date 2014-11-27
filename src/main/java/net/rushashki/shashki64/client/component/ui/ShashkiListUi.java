package net.rushashki.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.shashki64.client.cell.PlayCell;
import net.rushashki.shashki64.shared.model.dto.PlayDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 25.11.14
 * Time: 9:18
 */
public class ShashkiListUi extends Composite {

  interface ShashkiListUiUiBinder extends UiBinder<HTMLPanel, ShashkiListUi> {
  }

  private static ShashkiListUiUiBinder ourUiBinder = GWT.create(ShashkiListUiUiBinder.class);

  @UiField
  HTMLPanel shashkiList;

  CellList<PlayDto> partyList;

  public ShashkiListUi() {
    initWidget(ourUiBinder.createAndBindUi(this));

    partyList = new CellList<>(new PlayCell());

    List<PlayDto> plays = new ArrayList<>();

    PlayDto playDto = new PlayDto();
    playDto.setTitle("test1");
    playDto.setPlayerWhite("Alex");
    playDto.setPlayerBlack("Po");
    plays.add(playDto);

    playDto = new PlayDto();
    playDto.setTitle("test2");
    playDto.setPlayerWhite("Po");
    playDto.setPlayerBlack("Alex");
    plays.add(playDto);

    playDto = new PlayDto();
    playDto.setTitle("test1");
    playDto.setPlayerWhite("Master");
    playDto.setPlayerBlack("Bacalavr");
    plays.add(playDto);

    partyList.setRowData(plays);

    shashkiList.add(partyList);
  }

}