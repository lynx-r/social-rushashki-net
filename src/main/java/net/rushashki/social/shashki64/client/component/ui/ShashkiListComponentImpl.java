package net.rushashki.social.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.social.shashki64.shared.dto.PlayDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 25.11.14
 * Time: 9:18
 */
public class ShashkiListComponentImpl extends Composite {

  interface Binder extends UiBinder<HTMLPanel, ShashkiListComponentImpl> {
  }

  private static Binder ourUiBinder = GWT.create(Binder.class);

  @UiField
  HTMLPanel shashkiList;

  public ShashkiListComponentImpl() {
    initWidget(ourUiBinder.createAndBindUi(this));

    List<ShashkiViewComponentImpl> plays = new ArrayList<>();

    PlayDto playDto;
    for (int i = 0; i < 4; i++) {
      playDto = new PlayDto("test" + i, "master" + i, "bachelor" + i);
      ShashkiViewComponentImpl shashkiViewComponentImpl = new ShashkiViewComponentImpl(playDto);
      plays.add(shashkiViewComponentImpl);
      playDto.setDeskHtml(shashkiViewComponentImpl::toString);
    }

    for (ShashkiViewComponentImpl play : plays) {
      shashkiList.add(play);
    }
  }

}