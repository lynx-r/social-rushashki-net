package net.rushashki.social.shashki64.server.dao.impl;

import net.rushashki.social.shashki64.server.dao.GameDao;
import net.rushashki.social.shashki64.shared.model.entity.GameEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 31.12.14
 * Time: 17:34
 */
@Stateless
public class GameDaoImpl extends DaoImpl<GameEntity> implements GameDao {

  @Inject
  private EntityManager entityManager;

  public GameDaoImpl() {
    super(GameEntity.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

}
