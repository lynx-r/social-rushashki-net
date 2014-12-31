package net.rushashki.social.shashki64.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.rushashki.social.shashki64.client.rpc.PlayerMessageService;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessage;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.12.14
 * Time: 9:31
 */
public class PlayerMessageServiceImpl extends RemoteServiceServlet implements PlayerMessageService {
  @Inject
  private net.rushashki.social.shashki64.server.service.PlayerMessageService playerMessageService;

  @Override
  public List<PlayerMessage> getLastPlayerMessages(int countLast, Long playerId, Long opponentId) {
    return playerMessageService.findLastMessages(countLast, playerId, opponentId);
  }
}