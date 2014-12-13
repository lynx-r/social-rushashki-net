package net.rushashki.shashki64.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import net.rushashki.shashki64.shared.model.Shashist;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 05.12.14
 * Time: 20:03
 */
@RemoteServiceRelativePath("ProfileService")
public interface ProfileService extends RemoteService {

  public Boolean isAuthenticated();

  public Shashist getProfile();

  /**
   * Utility/Convenience class.
   * Use ProfileService.App.getInstance() to access static instance of ProfileServiceAsync
   */
  public static class App {
    private static final ProfileServiceAsync ourInstance = (ProfileServiceAsync) GWT.create(ProfileService.class);

    public static ProfileServiceAsync getInstance() {
      return ourInstance;
    }
  }
}
