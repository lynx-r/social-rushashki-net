package net.rushashki.social.shashki64.server.servlet.oauth;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 03.01.15
 * Time: 14:51
 */
public interface ClientSecrets {
  public String getClientId();
  public String getClientName();
  public String getClientSecret();
  public String getAuthUri();
  public String getTokenUri();
  public String getServerBaseUri();
  public String getExpiresOn();
  public List<String> getRedirectUris();
}
