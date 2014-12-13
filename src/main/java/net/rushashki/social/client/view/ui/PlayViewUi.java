package net.rushashki.social.client.view.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.social.client.view.PlayView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 14:58
 */
public class PlayViewUi extends BasicViewUi implements PlayView {

  private static PlayViewImplUiBinder ourUiBinder = GWT.create(PlayViewImplUiBinder.class);

  public PlayViewUi() {
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

  interface PlayViewImplUiBinder extends UiBinder<HTMLPanel, PlayViewUi> {
  }

}