package net.rushashki.social.shashki64.client.view.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.client.component.ui.ShashkiPlayComponentImpl;
import net.rushashki.social.shashki64.client.view.PlayView;
import net.rushashki.social.shashki64.client.websocket.GameWebsocket;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 14:58
 */
public class PlayViewImpl extends BasicViewUi implements PlayView {

  private static Binder ourUiBinder = GWT.create(Binder.class);

  @UiField(provided = true)
  ShashkiPlayComponentImpl shashkiPlay;

  public PlayViewImpl() {
    initWidget(ourUiBinder.createAndBindUi(this));
  }

  public PlayViewImpl(ClientFactory clientFactory) {
    new GameWebsocket(clientFactory);
    shashkiPlay = new ShashkiPlayComponentImpl(clientFactory);
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

  interface Binder extends UiBinder<HTMLPanel, PlayViewImpl> {
  }

}