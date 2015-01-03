package net.rushashki.social.shashki64.server.dao.impl;

import net.rushashki.social.shashki64.server.dao.Dao;
import net.rushashki.social.shashki64.shared.model.PersistableObject;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 15.11.14
 * Time: 17:00
 */
public abstract class DaoImpl<E extends PersistableObject> implements Dao<E> {
  private Class<E> entityClass;

  public DaoImpl(Class<E> entityClass) {
    this.entityClass = entityClass;
  }

  protected abstract EntityManager getEntityManager();

  public void create(E entity) {
    getEntityManager().persist(entity);
  }

  public void edit(E entity) {
    getEntityManager().merge(entity);
  }

  public void remove(E entity) {
    getEntityManager().remove(getEntityManager().merge(entity));
  }

  public E find(Object id) {
    if (id == null) {
      return null;
    }
    return getEntityManager().find(entityClass, id);
  }

  public List<E> findAll() {
    CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
    cq.select(cq.from(entityClass));
    return getEntityManager().createQuery(cq).getResultList();
  }

  public List<E> findPublishedAll() {
    CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
    CriteriaQuery cq = cb.createQuery(entityClass);
    Root<E> root = cq.from(entityClass);
    cq.select(root).where(cb.equal(root.get("published"), true));
    return getEntityManager().createQuery(cq).getResultList();
  }

  public List<E> findRange(int[] range) {
    CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
    cq.select(cq.from(entityClass));
    Query q = getEntityManager().createQuery(cq);
    q.setMaxResults(range[1] - range[0]);
    q.setFirstResult(range[0]);
    return q.getResultList();
  }

}
