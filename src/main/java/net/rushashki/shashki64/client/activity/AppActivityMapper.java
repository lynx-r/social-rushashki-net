package net.rushashki.shashki64.client.activity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.client.place.HomePlace;
import net.rushashki.shashki64.client.place.PlayPlace;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 14:54
 */
public class AppActivityMapper implements ActivityMapper {

  ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;

  @Override
  public Activity getActivity(Place place) {
    if (place instanceof HomePlace) {
      return new HomeActivity((HomePlace) place);
    } else if (place instanceof PlayPlace) {
      return new PlayActivity((PlayPlace) place);
    }
    return null;
  }
}
