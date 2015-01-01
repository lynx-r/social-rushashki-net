package net.rushashki.social.shashki64.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import net.rushashki.social.shashki64.client.place.PlayPlace;
import net.rushashki.social.shashki64.client.view.PlayView;
import net.rushashki.social.shashki64.client.view.ui.PlayViewUi;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 15:07
 */
public class PlayActivity extends BasicActivity implements PlayView.Presenter {

  public PlayActivity(PlayPlace playPlace) {
    super(playPlace.getToken());
    this.view = new PlayViewUi(playPlace.getClientFactory());
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
