package net.rushashki.shashki64.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 15:03
 */
public class PlayPlace extends Place {

  private String playerName;

  public PlayPlace(String token) {
    this.playerName = token;
  }

  public String getPlayerName() {
    return playerName;
  }

  public static class Tokenizer implements PlaceTokenizer<PlayPlace> {

    @Override
    public PlayPlace getPlace(String token) {
      return new PlayPlace(token);
    }

    @Override
    public String getToken(PlayPlace place) {
      return place.getPlayerName();
    }

  }

}
