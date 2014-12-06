package net.rushashki.shashki64.client.activity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import net.rushashki.shashki64.client.place.*;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 14:54
 */
public class AppActivityMapper implements ActivityMapper {

  @Override
  public Activity getActivity(Place place) {
    if (place instanceof HomePlace) {
      return new HomeActivity((HomePlace) place);
    } else if (place instanceof PlayLentaPlace) {
      return new PlayLentaActivity((PlayLentaPlace) place);
    } else if (place instanceof PlayPlace) {
      return new PlayActivity((PlayPlace) place);
    } else if (place instanceof SignInPlace) {
      return new SignInActivity((SignInPlace) place);
    } else if (place instanceof ProfilePlace) {
      return new ProfileActivity((ProfilePlace) place);
    } else if (place instanceof SettingsPlace) {
      return new SettingsActivity((SettingsPlace) place);
    }
    return null;
  }
}
