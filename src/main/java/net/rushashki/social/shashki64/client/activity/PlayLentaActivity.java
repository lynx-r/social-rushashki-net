package net.rushashki.social.shashki64.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import net.rushashki.social.shashki64.client.place.PlayLentaPlace;
import net.rushashki.social.shashki64.client.view.PlayLentaView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 12:41
 */
public class PlayLentaActivity extends BasicActivity implements PlayLentaView.Presenter {
  public PlayLentaActivity(PlayLentaPlace playLentaPlace) {
    super(playLentaPlace.getToken());
    this.view = shashkiGinjector.getPlayLentaView();
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
