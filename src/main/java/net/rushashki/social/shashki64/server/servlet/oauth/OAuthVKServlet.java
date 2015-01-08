package net.rushashki.social.shashki64.server.servlet.oauth;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.http.GenericUrl;
import net.rushashki.social.shashki64.server.config.OAuthClient;
import net.rushashki.social.shashki64.server.servlet.oauth.jsonfilesecrets.JsonFileRepository;
import net.rushashki.social.shashki64.server.util.Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 16.11.14
 * Time: 12:37
 */
@WebServlet(name = "OAuthVKServlet", urlPatterns = {"/OAuthVKServlet"})
public class OAuthVKServlet extends AbstractAuthorizationCodeServlet {

  private static ClientSecrets secrets;
  private List<String> scope = new ArrayList<>();

  @Override
  protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
    secrets = new JsonFileRepository(Utils.JSON_FACTORY).loadClientSecrets(OAuthVKServlet.class,
        Utils.CURRENT_SOCIAL_TYPE);
    return Utils.getFlow(secrets, scope);
  }

  @Override
  protected String getRedirectUri(HttpServletRequest httpServletRequest) throws ServletException, IOException {
    GenericUrl url = new GenericUrl(httpServletRequest.getRequestURL().toString());
    url.setRawPath(OAuthClient.REDIRECT_VK_CALLBACK_URL);
    return url.build();
  }

  @Override
  protected String getUserId(HttpServletRequest httpServletRequest) throws ServletException, IOException {
    return httpServletRequest.getSession(true).getId();
  }

}
