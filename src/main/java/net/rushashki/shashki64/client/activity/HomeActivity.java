package net.rushashki.shashki64.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import net.rushashki.shashki64.client.place.HomePlace;
import net.rushashki.shashki64.client.view.HomeView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 14:32
 */
public class HomeActivity extends BasicActivity implements HomeView.Presenter {

  public HomeActivity(HomePlace homePlace) {
    super(homePlace.getToken());
    this.view = shashkiGinjector.getHomeView();
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
