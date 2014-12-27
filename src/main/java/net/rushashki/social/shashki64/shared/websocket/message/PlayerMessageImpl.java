package net.rushashki.social.shashki64.shared.websocket.message;

import net.rushashki.social.shashki64.shared.model.Shashist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 07.12.14
 * Time: 18:56
 */
public class PlayerMessageImpl implements PlayerMessage {
  private Shashist sender;
  private Shashist receiver;
  private String message = "";
  private MessageType type;
  private List<Shashist> playerList = new ArrayList<>();
  private String data;

  @Override
  public Shashist getSender() {
    return sender;
  }

  @Override
  public void setSender(Shashist sender) {
    this.sender = sender;
  }

  @Override
  public Shashist getReceiver() {
    return receiver;
  }

  @Override
  public void setReceiver(Shashist receiver) {
    this.receiver = receiver;
  }

  @Override
  public String getMessage() {
    return message;
  }

  @Override
  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String getData() {
    return data;
  }

  @Override
  public void setData(String data) {
    this.data = data;
  }

  @Override
  public MessageType getType() {
    return type;
  }

  @Override
  public void setType(MessageType type) {
    this.type = type;
  }

  @Override
  public List<Shashist> getPlayerList() {
    return playerList;
  }

  @Override
  public void setPlayerList(List<Shashist> playerList) {
    this.playerList = playerList;
  }
}
