package net.rushashki.social.shashki64.client.view.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.social.shashki64.client.view.NotFoundView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 13.12.14
 * Time: 19:30
 */
public class NotFoundViewUi extends BasicViewUi implements NotFoundView {

  private static NotFoundViewUiUiBinder ourUiBinder = GWT.create(NotFoundViewUiUiBinder.class);

  public NotFoundViewUi() {
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

  interface NotFoundViewUiUiBinder extends UiBinder<HTMLPanel, NotFoundViewUi> {
  }

}