package net.rushashki.social.shashki64.client.view.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.social.shashki64.client.view.AboutUsView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 28.12.14
 * Time: 9:47
 */
public class AboutUsViewUi extends BasicViewUi implements AboutUsView {

  private static AboutUsViewUiUiBinder ourUiBinder = GWT.create(AboutUsViewUiUiBinder.class);

  public AboutUsViewUi() {
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

  interface AboutUsViewUiUiBinder extends UiBinder<HTMLPanel, AboutUsViewUi> {
  }

}