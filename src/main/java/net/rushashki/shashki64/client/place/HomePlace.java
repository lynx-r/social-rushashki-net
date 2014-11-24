package net.rushashki.shashki64.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 14:44
 */
public class HomePlace extends BasicPlace {

  public HomePlace(String token) {
    super(token);
  }

  public static class Tokenizer implements PlaceTokenizer<HomePlace> {

    @Override
    public HomePlace getPlace(String token) {
      return new HomePlace(token);
    }

    @Override
    public String getToken(HomePlace place) {
      return place.getToken();
    }

  }

}
