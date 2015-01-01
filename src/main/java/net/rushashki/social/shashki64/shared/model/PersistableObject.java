package net.rushashki.social.shashki64.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 12.12.14
 * Time: 10:20
 */
public interface PersistableObject extends IsSerializable {

  public Long getId();

  public Integer getVersion();

}
