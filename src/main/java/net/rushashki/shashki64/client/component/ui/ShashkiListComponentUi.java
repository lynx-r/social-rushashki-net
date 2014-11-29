package net.rushashki.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
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

    PlayDto playDto;
    for (int i = 0; i < 10; i++) {
      playDto = new PlayDto("test" + i, "master" + i, "bachelor" + i);
      ShashkiComponentUi shashkiComponentUi = new ShashkiComponentUi(playDto);
      plays.add(shashkiComponentUi);
      playDto.setDeskHtml(shashkiComponentUi::toString);
    }

    for (ShashkiComponentUi play : plays) {
      shashkiList.add(play);
    }
  }

}