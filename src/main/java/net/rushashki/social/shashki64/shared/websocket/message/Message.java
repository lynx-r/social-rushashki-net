package net.rushashki.social.shashki64.shared.websocket.message;

import net.rushashki.social.shashki64.shared.model.Shashist;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 01.10.14
 * Time: 22:51
 */
public interface Message {

  Shashist getSender();

  void setSender(Shashist sender);

  Shashist getReceiver();

  void setReceiver(Shashist receiver);

  String getMessage();

  void setMessage(String message);

  String getData();

  void setData(String data);

}
