package net.rushashki.social.shashki64.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.12.14
 * Time: 9:31
 */
@RemoteServiceRelativePath("PlayerMessageService")
public interface PlayerMessageService extends RemoteService {

  public List<PlayerMessage> getLastPlayerMessages(int countLast, Long playerId, Long opponentId);

  /**
   * Utility/Convenience class.
   * Use PlayerMessageService.App.getInstance() to access static instance of PlayerMessageServiceAsync
   */
  public static class App {
    private static final PlayerMessageServiceAsync ourInstance = (PlayerMessageServiceAsync) GWT.create(PlayerMessageService.class);

    public static PlayerMessageServiceAsync getInstance() {
      return ourInstance;
    }
  }
}
