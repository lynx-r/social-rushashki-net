package net.rushashki.social.shashki64.server.dao;

import net.rushashki.social.shashki64.shared.model.entity.PlayerMessageEntity;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessage;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 28.12.14
 * Time: 21:53
 */
public interface PlayerMessageDao extends Dao<PlayerMessageEntity> {
  List<PlayerMessage> findLastMessages(int countLast, Long playerId, Long opponentId);
}
