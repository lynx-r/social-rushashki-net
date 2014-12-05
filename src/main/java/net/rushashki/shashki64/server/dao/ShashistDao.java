package net.rushashki.shashki64.server.dao;

import net.rushashki.shashki64.shared.model.Shashist;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 05.12.14
 * Time: 0:18
 */
public interface ShashistDao extends Dao<Shashist> {

  public Shashist findByVkUid(String uid);

}
