package net.rushashki.social.shashki64.client.activity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.client.config.ShashkiGinjector;
import net.rushashki.social.shashki64.client.event.OnClientFactoryEvent;
import net.rushashki.social.shashki64.client.event.OnClientFactoryEventHandler;
import net.rushashki.social.shashki64.client.place.*;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 14:54
 */
public class AppActivityMapper implements ActivityMapper {

  private ClientFactory clientFactory;
  private ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  private EventBus eventBus;

  public AppActivityMapper() {
    this.eventBus = shashkiGinjector.getEventBus();

    eventBus.addHandler(OnClientFactoryEvent.TYPE, new OnClientFactoryEventHandler() {
      @Override
      public void onOnClientFactory(OnClientFactoryEvent event) {
        AppActivityMapper.this.clientFactory = event.getClientFactory();
      }
    });
  }

  @Override
  public Activity getActivity(Place place) {
    BasicPlace basicPlace = (BasicPlace) place;
    basicPlace.setClientFactory(clientFactory);
    if (place instanceof HomePlace) {
      return new HomeActivity((HomePlace) basicPlace);
    } else if (place instanceof PlayLentaPlace) {
      return new PlayLentaActivity((PlayLentaPlace) basicPlace);
    } else if (place instanceof SignInPlace) {
      return new SignInActivity((SignInPlace) basicPlace);
    }
    if (clientFactory.getPlayer() != null) {
      if (place instanceof PlayPlace) {
        return new PlayActivity((PlayPlace) basicPlace);
      } else if (place instanceof ProfilePlace) {
        return new ProfileActivity((ProfilePlace) basicPlace);
      } else if (place instanceof SettingsPlace) {
        return new SettingsActivity((SettingsPlace) basicPlace);
      }
    }
    return new NotFoundActivity();
  }

}
