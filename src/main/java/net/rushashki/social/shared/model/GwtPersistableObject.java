package net.rushashki.social.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 15.11.14
 * Time: 16:03
 */
@MappedSuperclass
public class GwtPersistableObject implements PersistableObject, IsSerializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Version
  private Integer version;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public Integer getVersion() {
    return version;
  }
}
