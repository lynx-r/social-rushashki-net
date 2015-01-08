package net.rushashki.social.shashki64.server.service;

import net.rushashki.social.shashki64.server.dao.Dao;
import net.rushashki.social.shashki64.shared.model.PersistableObject;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 05.12.14
 * Time: 0:07
 */
public abstract class BaseService<E extends PersistableObject> {
  protected abstract Dao<E> getDao();

  public void create(E entity) {
    getDao().create(entity);
  }

  public void edit(E entity) {
    getDao().edit(entity);
  }

  public void remove(E entity) {
    getDao().remove(entity);
  }

  public E find(Long id) {
    return getDao().find(id);
  }

  public List<E> findAll() {
    return getDao().findAll();
  }

  public List<E> findPublishedAll() {
    return getDao().findPublishedAll();
  }
}
