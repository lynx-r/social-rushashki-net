package net.rushashki.shashki64.server.service;

import net.rushashki.shashki64.server.dao.Dao;
import net.rushashki.shashki64.server.dao.ShashistDao;
import net.rushashki.shashki64.shared.model.Shashist;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 05.12.14
 * Time: 0:08
 */
@Stateless
public class ShashistService extends BaseService<Shashist> {

  @Inject
  private ShashistDao shashistDao;

  @Override
  protected Dao<Shashist> getDao() {
    return shashistDao;
  }

  public Shashist findByVkUid(String uid) {
    return shashistDao.findByVkUid(uid);
  }

  public Shashist findBySessionId(String sessionId) {
    return shashistDao.findBySessionId(sessionId);
  }
}
