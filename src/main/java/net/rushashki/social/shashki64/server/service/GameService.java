package net.rushashki.social.shashki64.server.service;

import net.rushashki.social.shashki64.server.dao.Dao;
import net.rushashki.social.shashki64.server.dao.GameDao;
import net.rushashki.social.shashki64.shared.model.Game;
import net.rushashki.social.shashki64.shared.model.entity.GameEntity;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 31.12.14
 * Time: 17:38
 */
@Stateless
public class GameService extends BaseService<GameEntity> {

  @Inject
  private GameDao gameDao;

  @Override
  protected Dao<GameEntity> getDao() {
    return gameDao;
  }

  public Game findLazyFalse(Long id) {
    return gameDao.findLazyFalse(id);
  }
}
