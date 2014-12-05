package net.rushashki.shashki64.server.dao.impl;

import net.rushashki.shashki64.server.dao.ShashistDao;
import net.rushashki.shashki64.shared.model.Shashist;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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
    Query queue = getEntityManager().createQuery("FROM Shashist WHERE vkUid = :vkUid");
    queue.setParameter("vkUid", uid);
    return (Shashist) queue.getSingleResult();
  }

}
