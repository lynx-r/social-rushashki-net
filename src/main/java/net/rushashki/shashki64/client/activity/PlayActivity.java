package net.rushashki.shashki64.client.activity;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import net.rushashki.shashki64.client.event.OnWebsocketPlayerMessageEvent;
import net.rushashki.shashki64.client.place.PlayPlace;
import net.rushashki.shashki64.client.view.PlayView;
import net.rushashki.shashki64.shared.websocket.message.PlayerMessage;
import net.rushashki.shashki64.shared.websocket.message.PlayerMessageImpl;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 15:07
 */
public class PlayActivity extends BasicActivity implements PlayView.Presenter {

  public PlayActivity(PlayPlace playPlace) {
    super(playPlace.getToken());
    this.view = shashkiGinjector.getPlayView();

    PlayerMessage playerMessage = GWT.create(PlayerMessageImpl.class);
    playerMessage.setType(PlayerMessage.MessageType.USER_LIST_UPDATE);
    this.eventBus.fireEvent(new OnWebsocketPlayerMessageEvent(playerMessage));
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    view.setPresenter(this);
    super.start(panel, eventBus);
  }

  @Override
  public void goTo(Place place) {
    placeController.goTo(place);
  }
}
