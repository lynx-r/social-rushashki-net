package net.rushashki.shashki64.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 06.12.14
 * Time: 11:07
 */
public class ProfilePlace extends BasicPlace {

  public ProfilePlace(String token) {
    super(token);
  }

  public static class Tokenizer implements PlaceTokenizer<ProfilePlace> {

    @Override
    public ProfilePlace getPlace(String token) {
      return new ProfilePlace(token);
    }

    @Override
    public String getToken(ProfilePlace place) {
      return place.getToken();
    }

  }

}
