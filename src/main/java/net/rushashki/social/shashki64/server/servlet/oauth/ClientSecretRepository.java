package net.rushashki.social.shashki64.server.servlet.oauth;

import net.rushashki.social.shashki64.server.util.Utils;

public interface ClientSecretRepository {
	ClientSecrets loadClientSecrets(Class context, Utils.SocialType socialType);
}
