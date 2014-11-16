package net.rushashki.shashki64.server.util;

import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 15.11.14
 * Time: 17:34
 */
@Singleton
public class Resources {

  @PersistenceContext(unitName = "shashki64PU")
  private EntityManager entityManager;

  @Produces
  public EntityManager getEntityManager() {
    return entityManager;
  }

}
