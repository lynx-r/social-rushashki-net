package net.rushashki.social.shashki64.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import net.rushashki.social.shashki64.shared.model.Shashist;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 05.12.14
 * Time: 20:03
 */
public interface ProfileRpcServiceAsync {

  void isAuthenticated(AsyncCallback<Boolean> async);

  void getProfile(Long shashistId, AsyncCallback<Shashist> async);

  void saveProfile(Shashist profile, AsyncCallback<Void> async);

  void getAuthProfile(AsyncCallback<Shashist> async);
}
