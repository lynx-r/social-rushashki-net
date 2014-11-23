package net.rushashki.shashki64.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.client.place.HomePlace;
import net.rushashki.shashki64.client.view.HomeView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 14:32
 */
public class HomeActivity extends AbstractActivity implements HomeView.Presenter {

  private ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;

  private HomeView homeView;

  private PlaceController placeController;

  private String name;

  public HomeActivity(HomePlace homePlace) {
    this.name = homePlace.getGreetingName();
    this.homeView = shashkiGinjector.getHomeView();
    this.placeController = shashkiGinjector.getPlaceController();
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    homeView.setName(name);
    homeView.setPresenter(this);
    panel.setWidget(homeView.asWidget());
  }

  @Override
  public void goTo(Place place) {
    placeController.goTo(place);
  }
}
