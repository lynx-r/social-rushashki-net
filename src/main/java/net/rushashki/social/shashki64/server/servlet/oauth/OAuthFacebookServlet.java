package net.rushashki.social.shashki64.server.servlet.oauth;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.http.GenericUrl;
import net.rushashki.social.shashki64.server.config.OAuthClient;
import net.rushashki.social.shashki64.server.util.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 18.11.14
 * Time: 20:28
 */
@WebServlet(name = "OAuthFacebookServlet", urlPatterns = {"/OAuthFacebookServlet"})
public class OAuthFacebookServlet extends AbstractAuthorizationCodeServlet {
  private String hostName;

  @Override
  protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
    return Util.getFlow(hostName, Util.OAuthProvider.FACEBOOK);
  }

  @Override
  protected String getRedirectUri(HttpServletRequest httpServletRequest) throws ServletException, IOException {
    GenericUrl url = new GenericUrl(httpServletRequest.getRequestURL().toString());
    url.setRawPath(OAuthClient.REDIRECT_FACEBOOK_CALLBACK_URL);
    return url.build();
  }

  @Override
  protected String getUserId(HttpServletRequest httpServletRequest) throws ServletException, IOException {
    hostName = httpServletRequest.getRemoteHost();
    return httpServletRequest.getSession(true).getId();
  }
}
