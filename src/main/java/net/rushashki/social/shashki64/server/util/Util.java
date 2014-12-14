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
import net.rushashki.social.shashki64.shared.websocket.message.MessageFactory;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessage;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 16.11.14
 * Time: 17:19
 */
public class Util {

  public static AuthorizationCodeFlow getFlow(String apiTokenServerUrl, String apiKey, String apiSecret,
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

  public static String serializePlayerMessageToJson(PlayerMessage message) {
    // Retrieve the AutoBean controller
    AutoBean<PlayerMessage> bean = AutoBeanUtils.getAutoBean(message);
    return AutoBeanCodex.encode(bean).getPayload();
  }

  public static PlayerMessage deserializeFromJson(String json) {
    MessageFactory messageFactory = AutoBeanFactorySource.create(MessageFactory.class);
    AutoBean<PlayerMessage> playerMessageBean = AutoBeanCodex.decode(messageFactory, PlayerMessage.class, json);
    return playerMessageBean.as();
  }

}
