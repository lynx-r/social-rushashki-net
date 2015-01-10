package net.rushashki.social.shashki64.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

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

  public MessageType getMessageType();

  public void setMessageType(MessageType messageType);

  public String getMessage();

  public void setMessage(String message);

  public String getData();

  public void setData(String data);

  public Date getSentDate();

  public void setSentDate(Date sentDate);

  public enum MessageType implements IsSerializable {

    CHAT_MESSAGE,
    CHAT_PRIVATE_MESSAGE,
    USER_LIST_UPDATE,
    PLAY_INVITE,
    PLAY_REJECT_INVITE,
    PLAY_START,
    PLAY_MOVE,
    PLAY_CANCEL_MOVE,
    PLAY_LEFT,
    PLAY_PROPOSE_DRAW,
    PLAY_ACCEPT_DRAW,
    PLAY_END,
    PLAY_SURRENDER,
    PLAYER_REGISTER

  }

}
