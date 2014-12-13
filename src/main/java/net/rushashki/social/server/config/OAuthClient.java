package net.rushashki.social.server.config;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 16.11.14
 * Time: 16:46
 */
public class OAuthClient {

  // VK
  public static final String API_VK_KEY = "4223337";

  public static final String API_VK_SECRET = "Ote722Ebyr1XsrMtVPku";

  public static final String API_VK_TOKEN_SERVER_URL = "https://api.vk.com/oauth/access_token";

  public static final String API_VK_AUTHORIZATION_SERVER_URL = "https://oauth.vk.com/authorize";

  public static final String API_VK_GET_USER_INFO = "https://api.vk.com/method/users.get";

  public static final String REDIRECT_VK_CALLBACK_URL = "/OAuthVKCallbackServlet";

  // Facebook

  public static final String API_FACEBOOK_KEY = "809826612407407";

  public static final String API_FACEBOOK_SECRET = "cd70b8162b08df923c89d4ba6177bc7a";

  public static final String API_FACEBOOK_TOKEN_SERVER_URL = "https://graph.facebook.com/oauth/access_token";

  public static final String API_FACEBOOK_AUTHORIZATION_SERVER_URL = "https://www.facebook.com/dialog/oauth";

  public static final String REDIRECT_FACEBOOK_CALLBACK_URL = "/OAuthFacebookCallbackServlet";
}
