package net.rushashki.social.shashki64.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.12.14
 * Time: 9:31
 */
public interface PlayerMessageServiceAsync {

  void getLastPlayerMessages(int countLast, Long playerId, Long opponentId, AsyncCallback<List<PlayerMessage>> async);

}
