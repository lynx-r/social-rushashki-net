package net.rushashki.social.shashki64.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import net.rushashki.social.shashki64.client.config.ShashkiGinjector;
import net.rushashki.social.shashki64.client.event.OnNavbarReloadEvent;
import net.rushashki.social.shashki64.client.view.BasicView;
import net.rushashki.social.shashki64.shared.locale.ShashkiConstants;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 24.11.14
 * Time: 14:14
 */
public class BasicActivity extends AbstractActivity {

  protected final com.google.web.bindery.event.shared.EventBus eventBus;
  protected ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  protected String token;
  protected PlaceController placeController;
  protected BasicView view;
  protected ShashkiConstants constants;

  public BasicActivity(String token) {
    this.token = token;
    this.eventBus = shashkiGinjector.getEventBus();
    this.placeController = shashkiGinjector.getPlaceController();
    this.constants = shashkiGinjector.getShashkiConstants();
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    view.setToken(token);
    eventBus.fireEvent(new OnNavbarReloadEvent(token));
    panel.setWidget(view.asWidget());
  }

}
