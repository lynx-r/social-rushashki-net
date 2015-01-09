package net.rushashki.social.shashki64.server.servlet.oauth;

import net.rushashki.social.shashki64.server.util.Util;

public interface ClientSecretRepository {
	ClientSecrets loadClientSecrets(Class context, Util.SocialType socialType);
}
