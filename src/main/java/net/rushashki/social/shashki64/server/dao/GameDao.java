package net.rushashki.social.shashki64.server.dao;

import net.rushashki.social.shashki64.shared.model.entity.GameEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 31.12.14
 * Time: 17:33
 */
public interface GameDao extends Dao<GameEntity> {
  GameEntity findLazyFalse(Long id);

  List<GameEntity> findRange(int start, int length);
}
