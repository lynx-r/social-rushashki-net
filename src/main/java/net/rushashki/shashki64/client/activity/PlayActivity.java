package net.rushashki.shashki64.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.client.event.NavbarReloadEvent;
import net.rushashki.shashki64.client.place.PlayPlace;
import net.rushashki.shashki64.client.view.PlayView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 15:07
 */
public class PlayActivity extends AbstractActivity implements PlayView.Presenter {

  private final com.google.web.bindery.event.shared.EventBus eventBus;
  private ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;

  private PlayView playView;
  private PlaceController placeController;

  private String token;

  public PlayActivity(PlayPlace playPlace) {
    this.token = playPlace.getPlayerName();
    this.playView = shashkiGinjector.getPlayView();
    this.placeController = shashkiGinjector.getPlaceController();
    this.eventBus = shashkiGinjector.getEventBus();
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    playView.setToken(token);
    playView.setPresenter(this);
    eventBus.fireEvent(new NavbarReloadEvent(token));
    panel.setWidget(playView.asWidget());
  }

  @Override
  public void goTo(Place place) {
    placeController.goTo(place);
  }
}
