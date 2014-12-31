package net.rushashki.social.shashki64.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import net.rushashki.social.shashki64.shared.model.Game;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 31.12.14
 * Time: 17:13
 */
@RemoteServiceRelativePath("GameRpcService")
public interface GameRpcService extends RemoteService {

  public Long createGame(Game game);

  public Game getGame(Long id);

  public void saveGame(Game game);

  /**
   * Utility/Convenience class.
   * Use GameRpcService.App.getInstance() to access static instance of GameRpcServiceAsync
   */
  public static class App {
    private static final GameRpcServiceAsync ourInstance = (GameRpcServiceAsync) GWT.create(GameRpcService.class);

    public static GameRpcServiceAsync getInstance() {
      return ourInstance;
    }
  }

}
