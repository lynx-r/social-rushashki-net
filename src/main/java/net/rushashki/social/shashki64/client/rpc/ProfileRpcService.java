package net.rushashki.social.shashki64.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import net.rushashki.social.shashki64.shared.model.Shashist;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 05.12.14
 * Time: 20:03
 */
@RemoteServiceRelativePath("ProfileRpcService")
public interface ProfileRpcService extends RemoteService {

  public Boolean isAuthenticated();

  public Shashist getProfile(Long shashistId);

  Shashist getAuthProfile();

  public void saveProfile(Shashist profile);

  /**
   * Utility/Convenience class.
   * Use ProfileRpcService.App.getInstance() to access static instance of ProfileRpcServiceAsync
   */
  public static class App {
    private static final ProfileRpcServiceAsync ourInstance = (ProfileRpcServiceAsync) GWT.create(ProfileRpcService.class);

    public static ProfileRpcServiceAsync getInstance() {
      return ourInstance;
    }
  }
}
