package net.rushashki.social.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import net.rushashki.social.shared.model.Shashist;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 05.12.14
 * Time: 20:03
 */
public interface ProfileServiceAsync {
  void isAuthenticated(AsyncCallback<Boolean> async);

  void getProfile(AsyncCallback<Shashist> async);
}
