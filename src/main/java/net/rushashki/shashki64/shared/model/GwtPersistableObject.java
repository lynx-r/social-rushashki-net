package net.rushashki.shashki64.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 15.11.14
 * Time: 16:03
 */
@MappedSuperclass
public class GwtPersistableObject implements IsSerializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Version
  private Integer version;

  public Long getId() {
    return this.id;
  }

  public Integer getVersion() {
    return this.version;
  }

}
