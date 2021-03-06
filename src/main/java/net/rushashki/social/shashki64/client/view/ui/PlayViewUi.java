package net.rushashki.social.shashki64.client.view.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.client.component.ui.ShashkiPlayComponentUi;
import net.rushashki.social.shashki64.client.view.PlayView;
import net.rushashki.social.shashki64.client.websocket.GameWebsocket;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 14:58
 */
public class PlayViewUi extends BasicViewUi implements PlayView {

  private static PlayViewImplUiBinder ourUiBinder = GWT.create(PlayViewImplUiBinder.class);

  @UiField(provided = true)
  ShashkiPlayComponentUi shashkiPlay;

  public PlayViewUi() {
    initWidget(ourUiBinder.createAndBindUi(this));
  }

  public PlayViewUi(ClientFactory clientFactory) {
    new GameWebsocket(clientFactory);
    shashkiPlay = new ShashkiPlayComponentUi(clientFactory);
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