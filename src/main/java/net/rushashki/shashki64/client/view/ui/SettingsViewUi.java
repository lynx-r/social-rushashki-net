package net.rushashki.shashki64.client.view.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.shashki64.client.view.SettingsView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 06.12.14
 * Time: 11:40
 */
public class SettingsViewUi extends BasicViewUi implements SettingsView {
  private static SettingsViewUiUiBinder ourUiBinder = GWT.create(SettingsViewUiUiBinder.class);

  public SettingsViewUi() {
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

  interface SettingsViewUiUiBinder extends UiBinder<HTMLPanel, SettingsViewUi> {
  }
}