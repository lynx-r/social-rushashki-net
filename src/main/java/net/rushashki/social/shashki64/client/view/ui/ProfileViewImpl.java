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
public class ProfileViewImpl extends BasicViewUi implements ProfileView {
  private static Binder ourUiBinder = GWT.create(Binder.class);

  public ProfileViewImpl() {
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

  interface Binder extends UiBinder<HTMLPanel, ProfileViewImpl> {
  }
}