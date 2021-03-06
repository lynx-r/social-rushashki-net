package net.rushashki.social.shashki64.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import net.rushashki.social.shashki64.shared.model.Game;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 31.12.14
 * Time: 17:13
 */
public interface GameRpcServiceAsync {

  void getGame(Long id, AsyncCallback<Game> async);

  void saveGame(Game game, AsyncCallback<Void> async);

  void createGame(Game game, AsyncCallback<Long> async);
}
