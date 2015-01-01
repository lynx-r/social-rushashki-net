package net.rushashki.social.shashki64.client.view.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.social.shashki64.client.view.ProfileView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 06.12.14
 * Time: 9:15
 */
public class ProfileViewUi extends BasicViewUi implements ProfileView {
  private static ProfileViewUiUiBinder ourUiBinder = GWT.create(ProfileViewUiUiBinder.class);

  public ProfileViewUi() {
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

  interface ProfileViewUiUiBinder extends UiBinder<HTMLPanel, ProfileViewUi> {
  }
}