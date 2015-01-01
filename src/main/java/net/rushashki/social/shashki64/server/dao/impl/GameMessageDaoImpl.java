package net.rushashki.social.shashki64.server.dao.impl;

import net.rushashki.social.shashki64.server.dao.PlayerMessageDao;
import net.rushashki.social.shashki64.shared.model.entity.GameMessageEntity;
import net.rushashki.social.shashki64.shared.model.GameMessage;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 28.12.14
 * Time: 21:55
 */
@Stateless
public class GameMessageDaoImpl extends DaoImpl<GameMessageEntity> implements PlayerMessageDao {

  @Inject
  private EntityManager entityManager;

  public GameMessageDaoImpl() {
    super(GameMessageEntity.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public List<GameMessage> findLastMessages(int countLast, Long playerId, Long opponentId) {
    Query query = getEntityManager().createQuery(
        "SELECT m " +
            " FROM GameMessageEntity m " +
            " JOIN FETCH m.sender " +
            " JOIN FETCH m.receiver " +
            " WHERE ((m.sender.id = :senderId AND m.receiver.id = :receiverId) " +
            " OR (m.receiver.id = :senderId AND m.sender.id = :receiverId)) " +
            " AND m.messageType = :messageType " +
            " ORDER BY m.sentDate DESC");
    query.setParameter("senderId", playerId);
    query.setParameter("receiverId", opponentId);
    query.setParameter("messageType", GameMessage.MessageType.CHAT_PRIVATE_MESSAGE);

    query.setMaxResults(countLast);

    List list = query.getResultList();
    Collections.reverse(list);
    return list;
  }

}
