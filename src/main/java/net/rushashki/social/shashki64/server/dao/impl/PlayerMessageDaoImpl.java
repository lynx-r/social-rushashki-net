package net.rushashki.social.shashki64.server.dao.impl;

import net.rushashki.social.shashki64.server.dao.PlayerMessageDao;
import net.rushashki.social.shashki64.shared.model.entity.PlayerMessageEntity;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessage;

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
public class PlayerMessageDaoImpl extends DaoImpl<PlayerMessageEntity> implements PlayerMessageDao {

  @Inject
  private EntityManager entityManager;

  public PlayerMessageDaoImpl() {
    super(PlayerMessageEntity.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public List<PlayerMessage> findLastMessages(int countLast, Long playerId, Long opponentId) {
    Query query = getEntityManager().createQuery(
        "SELECT m " +
            " FROM PlayerMessageEntity m " +
            " JOIN FETCH m.sender " +
            " JOIN FETCH m.receiver " +
            " WHERE ((m.sender.id = :senderId AND m.receiver.id = :receiverId) " +
            " OR (m.receiver.id = :senderId AND m.sender.id = :receiverId)) " +
            " AND m.type = 1 " + // type = 1 - обычное сообщение
            " ORDER BY m.sentDate DESC");
    query.setParameter("senderId", playerId);
    query.setParameter("receiverId", opponentId);

    query.setMaxResults(countLast);

    List list = query.getResultList();
    Collections.reverse(list);
    return list;
  }

}
