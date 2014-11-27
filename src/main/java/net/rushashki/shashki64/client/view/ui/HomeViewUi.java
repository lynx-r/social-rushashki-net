package net.rushashki.shashki64.client.view.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.shashki64.client.place.PlayPlace;
import net.rushashki.shashki64.client.view.HomeView;

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

  void onClickPlay(ClickEvent event) {
    presenter.goTo(new PlayPlace(token));
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  interface HomeViewImplUiBinder extends UiBinder<HTMLPanel, HomeViewUi> {
  }
}