package net.rushashki.shashki64.client.view.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.shashki64.client.view.SignInView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 24.11.14
 * Time: 12:37
 */
public class SignInViewUi extends BasicViewUi implements SignInView {
  private static LoginViewUiUiBinder ourUiBinder = GWT.create(LoginViewUiUiBinder.class);

  public SignInViewUi() {
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

  interface LoginViewUiUiBinder extends UiBinder<HTMLPanel, SignInViewUi> {
  }
}