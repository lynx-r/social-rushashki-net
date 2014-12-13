package net.rushashki.social.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 12:44
 */
public class PlayLentaPlace extends BasicPlace {

  public PlayLentaPlace(String token) {
    super(token);
  }

  public static class Tokenizer implements PlaceTokenizer<PlayLentaPlace> {

    @Override
    public PlayLentaPlace getPlace(String token) {
      return new PlayLentaPlace(token);
    }

    @Override
    public String getToken(PlayLentaPlace place) {
      return place.getToken();
    }

  }

}
