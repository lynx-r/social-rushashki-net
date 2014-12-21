package net.rushashki.social.shashki64.client.place;

import com.google.gwt.place.shared.Place;
import net.rushashki.social.shashki64.client.ClientFactory;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 24.11.14
 * Time: 14:11
 */
public class BasicPlace extends Place {

  private String token;
  private ClientFactory clientFactory;

  public BasicPlace(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setClientFactory(ClientFactory clientFactory) {
    this.clientFactory = clientFactory;
  }

  public ClientFactory getClientFactory() {
    return clientFactory;
  }
}
