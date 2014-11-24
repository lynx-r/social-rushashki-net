package net.rushashki.shashki64.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.client.event.NavbarReloadEvent;
import net.rushashki.shashki64.client.view.BasicView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 24.11.14
 * Time: 14:14
 */
public class BasicActivity extends AbstractActivity {

  private final com.google.web.bindery.event.shared.EventBus eventBus;
  protected ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  protected String token;
  protected PlaceController placeController;
  protected BasicView view;

  public BasicActivity(String token) {
    this.token = token;
    this.eventBus = shashkiGinjector.getEventBus();
    this.placeController = shashkiGinjector.getPlaceController();
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    view.setToken(token);
    eventBus.fireEvent(new NavbarReloadEvent(token));
    panel.setWidget(view.asWidget());
  }

}
