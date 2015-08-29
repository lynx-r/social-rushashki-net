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
public class NotFoundViewImpl extends BasicViewUi implements NotFoundView {

  private static Binder ourUiBinder = GWT.create(Binder.class);

  public NotFoundViewImpl() {
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

  interface Binder extends UiBinder<HTMLPanel, NotFoundViewImpl> {
  }

}