package net.rushashki.social.shashki64.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import net.rushashki.social.shashki64.client.place.SignInPlace;
import net.rushashki.social.shashki64.client.view.SignInView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 24.11.14
 * Time: 14:05
 */
public class SignInActivity extends BasicActivity implements SignInView.Presenter {

  public SignInActivity(SignInPlace signInPlace) {
    super(signInPlace.getToken());
    this.view = shashkiGinjector.getSignInView();
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
