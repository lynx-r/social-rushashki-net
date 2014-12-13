package net.rushashki.social.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 15:03
 */
public class PlayPlace extends BasicPlace {

  public PlayPlace(String token) {
    super(token);
  }

  public static class Tokenizer implements PlaceTokenizer<PlayPlace> {

    @Override
    public PlayPlace getPlace(String token) {
      return new PlayPlace(token);
    }

    @Override
    public String getToken(PlayPlace place) {
      return place.getToken();
    }

  }

}
