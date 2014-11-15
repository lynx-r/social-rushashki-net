package net.rushashki.shashki64.server.dao;

import net.rushashki.shashki64.share.model.PersistableObject;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 15.11.14
 * Time: 16:58
 */
public interface Dao<E extends PersistableObject> {
  public void create(E entity);

  public void edit(E entity);

  public void remove(E entity);

  public E find(Object id);

  public List<E> findAll();

  public List<E> findPublicAll();

  public List<E> findRange(int[] range);

}
