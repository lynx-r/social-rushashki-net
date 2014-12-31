package net.rushashki.social.shashki64.shared.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 01.10.14
 * Time: 22:51
 */
public interface Message extends PersistableObject {

  public Shashist getSender();

  public void setSender(Shashist sender);

  public Shashist getReceiver();

  public void setReceiver(Shashist receiver);

  public String getMessage();

  public void setMessage(String message);

  public String getData();

  public void setData(String data);

  public Date getSentDate();

  public void setSentDate(Date sentDate);

}
