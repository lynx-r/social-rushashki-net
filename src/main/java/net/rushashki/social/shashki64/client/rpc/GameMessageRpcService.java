package net.rushashki.social.shashki64.client.rpc;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import net.rushashki.social.shashki64.shared.model.GameMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.12.14
 * Time: 9:31
 */
@RemoteServiceRelativePath("GameMessageRpcService")
public interface GameMessageRpcService extends RemoteService {

  public List<GameMessage> getLastPlayerMessages(int countLast, Long playerId, Long opponentId);

  /**
   * Utility/Convenience class.
   * Use GameMessageService.App.fromString() to access static instance of GameMessageServiceAsync
   */
  public static class App {
    private static final GameMessageRpcServiceAsync ourInstance = (GameMessageRpcServiceAsync) GWT.create(GameMessageRpcService.class);

    public static GameMessageRpcServiceAsync getInstance() {
      return ourInstance;
    }
  }
}
