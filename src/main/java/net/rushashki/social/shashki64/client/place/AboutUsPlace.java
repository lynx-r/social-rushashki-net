package net.rushashki.social.shashki64.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 28.12.14
 * Time: 9:51
 */
public class AboutUsPlace extends BasicPlace {
  
  public AboutUsPlace(String token) {
    super(token);
  }

  public static class Tokenizer implements PlaceTokenizer<AboutUsPlace> {

    @Override
    public AboutUsPlace getPlace(String token) {
      return new AboutUsPlace(token);
    }

    @Override
    public String getToken(AboutUsPlace place) {
      return place.getToken();
    }

  }

}
