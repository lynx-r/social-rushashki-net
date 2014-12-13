package net.rushashki.social.server.servlet.oauth;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.http.GenericUrl;
import net.rushashki.social.server.config.ConfigHelper;
import net.rushashki.social.server.config.OAuthClient;
import net.rushashki.social.server.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 16.11.14
 * Time: 12:37
 */
@WebServlet(name = "OAuthVKServlet", urlPatterns = {"/OAuthVKServlet"})
public class OAuthVKServlet extends AbstractAuthorizationCodeServlet {

  @Override
  protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
    return Util.getFlow(OAuthClient.API_VK_TOKEN_SERVER_URL,
        OAuthClient.API_VK_KEY,
        OAuthClient.API_VK_SECRET,
        OAuthClient.API_VK_AUTHORIZATION_SERVER_URL,
        ConfigHelper.CREDENTIAL_STORE_FILE_PATH);
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
