package net.rushashki.shashki64.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 06.12.14
 * Time: 11:44
 */
public class SettingsPlace extends BasicPlace {

  public SettingsPlace(String token) {
    super(token);
  }

  public static class Tokenizer implements PlaceTokenizer<SettingsPlace> {

    @Override
    public SettingsPlace getPlace(String token) {
      return new SettingsPlace(token);
    }

    @Override
    public String getToken(SettingsPlace place) {
      return place.getToken();
    }

  }

}
