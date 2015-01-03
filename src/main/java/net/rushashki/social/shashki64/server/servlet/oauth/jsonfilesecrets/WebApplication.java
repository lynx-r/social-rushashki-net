package net.rushashki.social.shashki64.server.servlet.oauth.jsonfilesecrets;

import com.google.api.client.util.Key;

public class WebApplication {
	@Key("web_vk")
	private JsonClientSecrets secretVk;

	public JsonClientSecrets getSecretVk() {
		return secretVk;
	}
}
