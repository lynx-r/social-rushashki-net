package net.rushashki.social.shashki64.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 12:44
 */
public class PlayTapePlace extends BasicPlace {

  public PlayTapePlace(String token) {
    super(token);
  }

  public static class Tokenizer implements PlaceTokenizer<PlayTapePlace> {

    @Override
    public PlayTapePlace getPlace(String token) {
      return new PlayTapePlace(token);
    }

    @Override
    public String getToken(PlayTapePlace place) {
      return place.getToken();
    }

  }

}
