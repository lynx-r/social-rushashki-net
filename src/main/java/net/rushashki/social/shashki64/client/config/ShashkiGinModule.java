package net.rushashki.social.shashki64.client.config;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.PlaceController;
import com.google.inject.Provider;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.client.ClientFactoryImpl;
import net.rushashki.social.shashki64.client.rpc.GameRpcServiceAsync;
import net.rushashki.social.shashki64.client.rpc.GameMessageRpcServiceAsync;
import net.rushashki.social.shashki64.client.rpc.ProfileRpcServiceAsync;
import net.rushashki.social.shashki64.client.util.ShashkiLogger;
import net.rushashki.social.shashki64.client.view.*;
import net.rushashki.social.shashki64.client.view.ui.*;
import net.rushashki.social.shashki64.shared.config.ShashkiConfiguration;
import net.rushashki.social.shashki64.shared.locale.ShashkiConstants;

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
    bind(ClientFactory.class).to(ClientFactoryImpl.class).in(Singleton.class);

    bind(ShashkiLogger.class).in(Singleton.class);

    bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);

    bind(PlaceController.class).toProvider(PlaceProvider.class).in(Singleton.class);

    bind(ShashkiConstants.class).in(Singleton.class);
    bind(ShashkiConfiguration.class).in(Singleton.class);

    bind(HomeView.class).to(HomeViewImpl.class).in(Singleton.class);
    bind(PlayTapeView.class).to(PlayTapeViewImpl.class).in(Singleton.class);
    bind(PlayView.class).to(PlayViewImpl.class).in(Singleton.class);
    bind(SignInView.class).to(SignInViewImpl.class).in(Singleton.class);
    bind(ProfileView.class).to(ProfileViewImpl.class).in(Singleton.class);
    bind(SettingsView.class).to(SettingsViewImpl.class).in(Singleton.class);
    bind(AboutUsView.class).to(AboutUsViewImpl.class).in(Singleton.class);
    bind(NotFoundView.class).to(NotFoundViewImpl.class).in(Singleton.class);

    bind(ProfileRpcServiceAsync.class).in(Singleton.class);
    bind(GameMessageRpcServiceAsync.class).in(Singleton.class);
    bind(GameRpcServiceAsync.class).in(Singleton.class);
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
