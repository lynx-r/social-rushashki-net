package net.rushashki.social.shashki64.server.servlet.oauth;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import net.rushashki.social.shashki64.server.config.OAuthClient;
import net.rushashki.social.shashki64.server.config.ServerConfiguration;
import net.rushashki.social.shashki64.server.service.ShashistService;
import net.rushashki.social.shashki64.server.util.Util;
import net.rushashki.social.shashki64.shared.model.entity.ShashistEntity;

import javax.inject.Inject;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 16.11.14
 * Time: 15:55
 */
@WebServlet(name = "OAuthVKCallbackServlet", urlPatterns = {"/OAuthVKCallbackServlet"})
public class OAuthVKCallbackServlet extends AbstractAuthorizationCodeCallbackServlet {

  @Inject
  private ShashistService shashistService;

  @Inject
  private ServerConfiguration serverConfiguration;

  private List<String> scopes = new ArrayList<>();

  @Override
  protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential) throws ServletException, IOException {
    String accessToken = credential.getAccessToken();
    GenericUrl url = new GenericUrl(OAuthClient.API_VK_GET_USER_INFO);
    url.set("access_token", accessToken);

    HttpRequest request = Util.HTTP_TRANSPORT.createRequestFactory().buildGetRequest(url);
    HttpResponse response = request.execute();
    InputStream inputStream = response.getContent();
    JsonReader jsonReader = Json.createReader(inputStream);
    JsonObject responseObject = jsonReader.readObject();

    if (responseObject.getJsonArray("response").isEmpty()) {
      resp.sendRedirect("/404.html");
      return;
    }

    JsonArray usersArray = responseObject.getJsonArray("response");
    JsonObject array = usersArray.getJsonObject(0);
    JsonNumber uid = array.getJsonNumber("uid");
    String vkUid = uid.toString();

    ShashistEntity shashistEntity = shashistService.findByVkUid(vkUid);
    if (shashistEntity == null) {
      JsonString firstName = array.getJsonString("first_name");
      JsonString lastName = array.getJsonString("last_name");
      shashistEntity = new ShashistEntity();
      shashistEntity.setVkUid(vkUid);
      shashistEntity.setFirstName(firstName.getString());
      shashistEntity.setLastName(lastName.getString());
    } else {
      shashistEntity.setVisitCounter(shashistEntity.getVisitCounter() + 1);
    }

    HttpSession session = req.getSession();
    if (shashistEntity.getSessionId() == null
        || !shashistEntity.getSessionId().equals(session.getId())) {
      shashistEntity.setSessionId(session.getId());
      if (shashistEntity.getId() == null) {
        shashistService.create(shashistEntity);
      } else {
        shashistService.edit(shashistEntity);
      }
    }

    resp.sendRedirect("/");
  }

  @Override
  protected void onError(HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
      throws ServletException, IOException {
    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
  }

  @Override
  protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
    ClientSecrets clientSecrets = new ClientSecrets(serverConfiguration, ClientSecrets.SocialType.VK);
    return Util.getFlow(clientSecrets, scopes);
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
