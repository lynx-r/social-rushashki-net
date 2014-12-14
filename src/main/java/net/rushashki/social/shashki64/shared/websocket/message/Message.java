package net.rushashki.social.shashki64.shared.websocket.message;

import net.rushashki.social.shashki64.shared.model.Shashist;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 01.10.14
 * Time: 22:51
 */
public interface Message {

  public Shashist getSender();

  public void setSender(Shashist sender);

  public Shashist getReceiver();

  public void setReceiver(Shashist receiver);

  public String getMessage();

  public void setMessage(String message);

}
