package net.rushashki.shashki64.client.config;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import net.rushashki.shashki64.client.util.ShashkiLogger;
import net.rushashki.shashki64.client.view.HomeView;
import net.rushashki.shashki64.client.view.PlayView;
import net.rushashki.shashki64.client.view.SignInView;
import net.rushashki.shashki64.client.view.ui.HomeViewUi;
import net.rushashki.shashki64.client.view.ui.PlayViewUi;
import net.rushashki.shashki64.client.view.ui.SignInViewUi;
import net.rushashki.shashki64.shared.locale.ShashkiConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 13:23
 */
public class ShashkiGinModule extends AbstractGinModule {

  @Override
  protected void configure() {
    bind(ShashkiLogger.class).in(Singleton.class);

    bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

    bind(PlaceController.class).toProvider(PlaceProvider.class).in(Singleton.class);

    bind(ShashkiConstants.class).in(Singleton.class);

    bind(HomeView.class).to(HomeViewUi.class).in(Singleton.class);
    bind(PlayView.class).to(PlayViewUi.class).in(Singleton.class);
    bind(SignInView.class).to(SignInViewUi.class).in(Singleton.class);
  }

  static class PlaceProvider implements Provider<PlaceController> {

    @Inject
    EventBus eventBus;

    @Override
    public PlaceController get() {
      return new PlaceController(eventBus);
    }
  }

}
