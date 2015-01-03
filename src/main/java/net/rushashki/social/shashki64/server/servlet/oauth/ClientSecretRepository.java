package net.rushashki.social.shashki64.server.servlet.oauth;

public interface ClientSecretRepository {
	public ClientSecrets loadClientSecrets(Class c);
}
