package net.rushashki.social.shashki64.client.activity;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import net.rushashki.social.shashki64.client.view.NotFoundView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 13.12.14
 * Time: 19:31
 */
public class NotFoundActivity extends BasicActivity implements NotFoundView.Presenter {

  public NotFoundActivity() {
    super("pageNotFound");
    this.view = shashkiGinjector.getNotFoundView();
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
