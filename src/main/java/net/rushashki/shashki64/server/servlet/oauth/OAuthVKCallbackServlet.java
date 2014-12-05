package net.rushashki.shashki64.server.servlet.oauth;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeCallbackServlet;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import net.rushashki.shashki64.server.config.ConfigHelper;
import net.rushashki.shashki64.server.config.OAuthClient;
import net.rushashki.shashki64.server.service.ShashistService;
import net.rushashki.shashki64.server.util.Util;
import net.rushashki.shashki64.shared.model.Shashist;

import javax.inject.Inject;
import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

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

  private static HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

  @Override
  protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential) throws ServletException, IOException {
    String accessToken = credential.getAccessToken();
    GenericUrl url = new GenericUrl(OAuthClient.API_VK_GET_USER_INFO);
    url.set("access_token", accessToken);

    HttpRequest request = HTTP_TRANSPORT.createRequestFactory().buildGetRequest(url);
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

    Shashist shashist = shashistService.findByVkUid(vkUid);
    if (shashist == null) {
      JsonString firstName = array.getJsonString("first_name");
      JsonString lastName = array.getJsonString("last_name");
      shashist = new Shashist();
      shashist.setVkUid(vkUid);
      shashist.setFirstName(firstName.getString());
      shashist.setLastName(lastName.getString());
      shashistService.create(shashist);
    }

    req.getSession().setAttribute("isAuthenticated", true);
    req.getSession().setAttribute("authProvider", "vk");
    req.getSession().setAttribute("firstName", shashist.getFirstName());
    req.getSession().setAttribute("lastName", shashist.getLastName());

    resp.sendRedirect("/");
  }

  @Override
  protected void onError(HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse) throws ServletException, IOException {
    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
  }

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
