package net.rushashki.social.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 24.11.14
 * Time: 14:13
 */
public class SignInPlace extends BasicPlace {

  public SignInPlace(String token) {
    super(token);
  }

  public static class Tokenizer implements PlaceTokenizer<SignInPlace> {

    @Override
    public SignInPlace getPlace(String token) {
      return new SignInPlace(token);
    }

    @Override
    public String getToken(SignInPlace place) {
      return place.getToken();
    }

  }

}
