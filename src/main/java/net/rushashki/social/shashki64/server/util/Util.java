package net.rushashki.social.shashki64.server.util;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.autobean.shared.AutoBeanUtils;
import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;
import net.rushashki.social.shashki64.server.config.ConfigHelper;
import net.rushashki.social.shashki64.server.config.OAuthClient;
import net.rushashki.social.shashki64.shared.model.GameMessage;
import net.rushashki.social.shashki64.shared.websocket.message.MessageFactory;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 16.11.14
 * Time: 17:19
 */
public class Util {

  public static final String LOCALHOST = "127.0.0.1";

  public static AuthorizationCodeFlow getFlow(String hostName, OAuthProvider provider) throws IOException {
    String apiKey;
    String apiSecret;

    switch (provider) {
      case VK:
        switch (hostName) {
          case Util.LOCALHOST:
            apiKey = OAuthClient.API_VK_KEY_LOCALHOST;
            apiSecret = OAuthClient.API_VK_SECRET_LOCALHOST;
            break;
          default:
            apiKey = OAuthClient.API_VK_KEY_LOCALHOST;
            apiSecret = OAuthClient.API_VK_SECRET_LOCALHOST;
            break;
        }
        return Util.getFlow(OAuthClient.API_VK_TOKEN_SERVER_URL,
            apiKey,
            apiSecret,
            OAuthClient.API_VK_AUTHORIZATION_SERVER_URL,
            ConfigHelper.CREDENTIAL_STORE_FILE_PATH);
      case FACEBOOK:
        switch (hostName) {
          case Util.LOCALHOST:
            apiKey = OAuthClient.API_FACEBOOK_KEY_LOCALHOST;
            apiSecret = OAuthClient.API_FACEBOOK_SECRET_LOCALHOST;
            break;
          default:
            apiKey = OAuthClient.API_FACEBOOK_KEY_LOCALHOST;
            apiSecret = OAuthClient.API_FACEBOOK_SECRET_LOCALHOST;
            break;
        }
        return Util.getFlow(OAuthClient.API_FACEBOOK_TOKEN_SERVER_URL,
            apiKey,
            apiSecret,
            OAuthClient.API_FACEBOOK_AUTHORIZATION_SERVER_URL,
            ConfigHelper.CREDENTIAL_STORE_FILE_PATH);
      default:
        return null;
    }
  }

  public enum OAuthProvider {
    VK,
    FACEBOOK
  }

  private static AuthorizationCodeFlow getFlow(String apiTokenServerUrl, String apiKey, String apiSecret,
                                               String apiAuthorizationServerUrl,
                                               String credentialStoreFilePath) throws IOException {
    return new AuthorizationCodeFlow.Builder(BearerToken.authorizationHeaderAccessMethod(),
        new NetHttpTransport(),
        new JacksonFactory(),
        new GenericUrl(apiTokenServerUrl),
        new ClientParametersAuthentication(apiKey, apiSecret),
        apiKey,
        apiAuthorizationServerUrl)
        .setCredentialDataStore(StoredCredential.getDefaultDataStore(new FileDataStoreFactory(
            new File(credentialStoreFilePath))))
        .build();
  }

  public static String inputStreamToString(InputStream inputStream) throws IOException {
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    return bufferedReader.readLine();
  }

  public static String serializePlayerMessageToJson(GameMessage message) {
    // Retrieve the AutoBean controller
    AutoBean<GameMessage> bean = AutoBeanUtils.getAutoBean(message);
    return AutoBeanCodex.encode(bean).getPayload();
  }

  public static GameMessage deserializeFromJson(String json) {
    MessageFactory messageFactory = AutoBeanFactorySource.create(MessageFactory.class);
    AutoBean<GameMessage> playerMessageBean = AutoBeanCodex.decode(messageFactory, GameMessage.class, json);
    return playerMessageBean.as();
  }

}
