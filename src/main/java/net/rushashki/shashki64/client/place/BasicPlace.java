package net.rushashki.shashki64.client.place;

import com.google.gwt.place.shared.Place;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 24.11.14
 * Time: 14:11
 */
public class BasicPlace extends Place {

  private String token;

  public BasicPlace(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

}
