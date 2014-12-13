package net.rushashki.social.client.view.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.social.client.view.HomeView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 12:32
 */
public class HomeViewUi extends BasicViewUi implements HomeView {

  private static HomeViewImplUiBinder ourUiBinder = GWT.create(HomeViewImplUiBinder.class);

  public HomeViewUi() {
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

  interface HomeViewImplUiBinder extends UiBinder<HTMLPanel, HomeViewUi> {
  }

}