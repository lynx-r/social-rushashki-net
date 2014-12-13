package net.rushashki.social.shared.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 15.11.14
 * Time: 15:49
 */
@Entity
@Table(name = "message")
public class Message extends GwtPersistableObject {

  @Size(min = 10, max = 5000)
  private String message;

  public Message() {}

  public Message(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

}
