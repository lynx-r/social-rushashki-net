package net.rushashki.shashki64.server.util;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.BearerToken;
import com.google.api.client.auth.oauth2.ClientParametersAuthentication;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.io.File;
import java.io.IOException;

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
}
