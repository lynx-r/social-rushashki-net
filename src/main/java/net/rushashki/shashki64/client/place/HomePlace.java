package net.rushashki.shashki64.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 14:44
 */
public class HomePlace extends Place {

  private String greetingName;

  public HomePlace(String token) {
    this.greetingName = token;
  }

  public String getGreetingName() {
    return greetingName;
  }

  public static class Tokenizer implements PlaceTokenizer<HomePlace> {

    @Override
    public HomePlace getPlace(String token) {
      return new HomePlace(token);
    }

    @Override
    public String getToken(HomePlace place) {
      return place.getGreetingName();
    }
  }

}
