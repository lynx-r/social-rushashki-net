package net.rushashki.shashki64.server.dao;

import net.rushashki.shashki64.shared.model.ShashistEntity;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 05.12.14
 * Time: 0:18
 */
public interface ShashistDao extends Dao<ShashistEntity> {

  public ShashistEntity findByVkUid(String uid);

  ShashistEntity findBySessionId(String sessionId);
}
