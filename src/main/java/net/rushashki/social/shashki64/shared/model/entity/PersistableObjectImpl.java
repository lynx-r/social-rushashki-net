package net.rushashki.social.shashki64.shared.model.entity;

import net.rushashki.social.shashki64.shared.model.PersistableObject;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 15.11.14
 * Time: 16:03
 */
@MappedSuperclass
public class PersistableObjectImpl implements PersistableObject {

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
