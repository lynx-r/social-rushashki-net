package net.rushashki.social.shashki64.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import net.rushashki.social.shashki64.client.place.PlayTapePlace;
import net.rushashki.social.shashki64.client.view.PlayTapeView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 12:41
 */
public class PlayTapeActivity extends BasicActivity implements PlayTapeView.Presenter {
  public PlayTapeActivity(PlayTapePlace playTapePlace) {
    super(playTapePlace.getToken());
    this.view = shashkiGinjector.getPlayTapeView();
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
