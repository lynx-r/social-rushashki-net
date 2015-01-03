package net.rushashki.social.shashki64.server.servlet.oauth.jsonfilesecrets;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Preconditions;
import net.rushashki.social.shashki64.server.servlet.oauth.ClientSecretRepository;
import net.rushashki.social.shashki64.server.servlet.oauth.ClientSecrets;

import java.io.IOException;
import java.io.InputStreamReader;

public class JsonFileRepository implements ClientSecretRepository {
	private static final String CLIENT_SECRETS_FILENAME = "client_secrets.json";
	private JsonFactory jsonFactory;

	public JsonFileRepository(JsonFactory jsonFactory) {
		this.jsonFactory = jsonFactory;
	}

	@Override
	public ClientSecrets loadClientSecrets(Class context) {
		InputStreamReader clientSecrets = new InputStreamReader(context.getResourceAsStream("/" + CLIENT_SECRETS_FILENAME));
		WebApplication app = null;
		try {
			app = jsonFactory.createJsonParser(clientSecrets).parseAndClose(WebApplication.class, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		Preconditions.checkArgument(!app.getSecretVk().getClientId().startsWith("Enter ")
						&& !app.getSecretVk().getClientSecret().startsWith("Enter "),
				"The sample code does not contain valid client id and secret values.\n"
						+ "Please add a Permitted App in your Member Profile, \n"
						+ "download the %s file from the VersionOne permitted applications page, \n"
						+ "and save it in the src/main/resources directory.\n",
				CLIENT_SECRETS_FILENAME);
		return app.getSecretVk();
	}

}