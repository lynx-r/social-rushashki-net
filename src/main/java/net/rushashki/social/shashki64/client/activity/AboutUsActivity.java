package net.rushashki.social.shashki64.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import net.rushashki.social.shashki64.client.place.AboutUsPlace;
import net.rushashki.social.shashki64.client.view.AboutUsView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 28.12.14
 * Time: 9:53
 */
public class AboutUsActivity extends BasicActivity implements AboutUsView.Presenter {

  public AboutUsActivity(AboutUsPlace place) {
    super(place.getToken());
    this.view = shashkiGinjector.getAboutUsView();
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
