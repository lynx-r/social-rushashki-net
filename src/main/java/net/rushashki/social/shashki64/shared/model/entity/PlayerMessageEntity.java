package net.rushashki.social.shashki64.shared.model.entity;

import net.rushashki.social.shashki64.shared.model.Shashist;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessage;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 15.11.14
 * Time: 15:49
 */
@Entity
@Table(name = "player_message")
public class PlayerMessageEntity extends PersistableObjectImpl implements PlayerMessage {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sender_id")
  private ShashistEntity sender;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "receiver_id")
  private ShashistEntity receiver;
  private String message;
  @Enumerated(EnumType.STRING)
  private MessageType type;
  private String data;
  @Column(name = "sent_date")
  private Date sentDate;

  @Override
  public Shashist getSender() {
    return sender;
  }

  @Override
  public void setSender(Shashist sender) {
  }

  public void setSender(ShashistEntity entity) {
    this.sender = entity;
  }

  @Override
  public Shashist getReceiver() {
    return receiver;
  }

  @Override
  public void setReceiver(Shashist receiver) {
  }

  public void setReceiver(ShashistEntity entity) {
    this.receiver = entity;
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
  public Date getSentDate() {
    return sentDate;
  }

  @Override
  public void setSentDate(Date sentDate) {
    this.sentDate = sentDate;
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
    return null;
  }

  @Override
  public void setPlayerList(List<Shashist> playerList) {
  }

}
