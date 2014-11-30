package net.rushashki.shashki64.client.view.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.shashki64.client.component.ui.ShashkiListComponentUi;
import net.rushashki.shashki64.client.view.PlayLentaView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 12:34
 */
public class PlayLentaViewUi extends BasicViewUi implements PlayLentaView {
  private static PlayLentaViewUiUiBinder ourUiBinder = GWT.create(PlayLentaViewUiUiBinder.class);
  @UiField
  ShashkiListComponentUi shashkiLenta;

  public PlayLentaViewUi() {
    initWidget(ourUiBinder.createAndBindUi(this));
  }

  @Override
  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  interface PlayLentaViewUiUiBinder extends UiBinder<HTMLPanel, PlayLentaViewUi> {
  }
}