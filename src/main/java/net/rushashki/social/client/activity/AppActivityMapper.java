package net.rushashki.social.client.activity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.social.client.config.ShashkiGinjector;
import net.rushashki.social.client.event.OnGetProfileEvent;
import net.rushashki.social.client.place.*;
import net.rushashki.social.shared.model.Shashist;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 14:54
 */
public class AppActivityMapper implements ActivityMapper {

  private Shashist player;
  private ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  private EventBus eventBus;

  public AppActivityMapper() {
    this.eventBus = shashkiGinjector.getEventBus();

    eventBus.addHandler(OnGetProfileEvent.TYPE, (event) -> {
      this.player = event.getProfile();
    });
  }

  @Override
  public Activity getActivity(Place place) {
    if (place instanceof HomePlace) {
      return new HomeActivity((HomePlace) place);
    } else if (place instanceof PlayLentaPlace) {
      return new PlayLentaActivity((PlayLentaPlace) place);
    } else if (place instanceof SignInPlace) {
      return new SignInActivity((SignInPlace) place);
    }
    if (player != null) {
      if (place instanceof PlayPlace) {
        return new PlayActivity((PlayPlace) place);
      } else if (place instanceof ProfilePlace) {
        return new ProfileActivity((ProfilePlace) place);
      } else if (place instanceof SettingsPlace) {
        return new SettingsActivity((SettingsPlace) place);
      }
    }
    return new NotFoundActivity();
  }

}
