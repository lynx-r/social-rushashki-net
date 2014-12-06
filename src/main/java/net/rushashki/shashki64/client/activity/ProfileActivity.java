package net.rushashki.shashki64.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import net.rushashki.shashki64.client.place.ProfilePlace;
import net.rushashki.shashki64.client.view.ProfileView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 06.12.14
 * Time: 11:09
 */
public class ProfileActivity extends BasicActivity implements ProfileView.Presenter {

  public ProfileActivity(ProfilePlace place) {
    super(place.getToken());
    this.view = shashkiGinjector.getProfileView();
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
