package net.rushashki.shashki64.server.dao.impl;

import net.rushashki.shashki64.server.dao.ShashistDao;
import net.rushashki.shashki64.shared.model.Shashist;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 05.12.14
 * Time: 0:19
 */
@Stateless
public class ShashistDaoImpl extends DaoImpl<Shashist> implements ShashistDao {

  @Inject
  private EntityManager entityManager;

  public ShashistDaoImpl() {
    super(Shashist.class);
  }

  @Override
  protected EntityManager getEntityManager() {
    return entityManager;
  }

  @Override
  public Shashist findByVkUid(String uid) {
    Query query = getEntityManager().createQuery("FROM Shashist WHERE vkUid = :vkUid");
    query.setParameter("vkUid", uid);
    return (Shashist) query.getSingleResult();
  }

  @Override
  public Shashist findBySessionId(String sessionId) {
    Query query = getEntityManager().createQuery("FROM Shashist WHERE sessionId = :sessionId");
    query.setParameter("sessionId", sessionId);
    List result = query.getResultList();
    if (result.isEmpty()) {
      return null;
    }
    return (Shashist) result.get(0);
  }

}
