package net.rushashki.shashki64.server.dao.impl;

import net.rushashki.shashki64.server.dao.MessageDao;
import net.rushashki.shashki64.shared.model.Message;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 15.11.14
 * Time: 16:40
 */
@Stateless
public class MessageDaoImpl extends DaoImpl<Message> implements MessageDao {

  @Inject
  private EntityManager entityManager;

  public MessageDaoImpl() {
    super(Message.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }
}
