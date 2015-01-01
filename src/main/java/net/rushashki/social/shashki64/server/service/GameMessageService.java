package net.rushashki.social.shashki64.server.service;

import net.rushashki.social.shashki64.server.dao.Dao;
import net.rushashki.social.shashki64.server.dao.PlayerMessageDao;
import net.rushashki.social.shashki64.shared.model.entity.GameMessageEntity;
import net.rushashki.social.shashki64.shared.model.GameMessage;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 28.12.14
 * Time: 21:52
 */
@Stateless
public class GameMessageService extends BaseService<GameMessageEntity> {

  @Inject
  private PlayerMessageDao playerMessageDao;

  @Override
  protected Dao<GameMessageEntity> getDao() {
    return playerMessageDao;
  }

  public List<GameMessage> findLastMessages(int countLast, Long playerId, Long opponentId) {
    return playerMessageDao.findLastMessages(countLast, playerId, opponentId);
  }
}
