package net.rushashki.shashki64.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 05.12.14
 * Time: 20:03
 */
public interface ProfileServiceAsync {
  void isAuthenticated(AsyncCallback<Boolean> async);
}
