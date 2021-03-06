package net.rushashki.social.shashki64.shared.model.entity;

import net.rushashki.social.shashki64.shared.model.Shashist;
import net.rushashki.social.shashki64.shared.model.GameMessage;

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
@Table(name = "game_message")
public class GameMessageEntity extends PersistableObjectImpl implements GameMessage {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sender_id")
  private ShashistEntity sender;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "receiver_id")
  private ShashistEntity receiver;

  private String message;

  @Enumerated(EnumType.STRING)
  @Column(name = "message_type")
  private MessageType messageType;

  private String data;

  @Column(name = "sent_date")
  private Date sentDate;

  @Column(name = "start_step")
  private String startStep;

  @Column(name = "end_step")
  private String endStep;

  private String captured;

  @Column(name = "game_id")
  private Long gameId;

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
  public MessageType getMessageType() {
    return messageType;
  }

  @Override
  public void setMessageType(MessageType messageType) {
    this.messageType = messageType;
  }

  @Override
  public List<Shashist> getPlayerList() {
    return null;
  }

  @Override
  public void setPlayerList(List<Shashist> playerList) {
  }

  @Override
  public String getStartStep() {
    return startStep;
  }

  @Override
  public void setStartStep(String startStep) {
    this.startStep = startStep;
  }

  @Override
  public String getEndStep() {
    return endStep;
  }

  @Override
  public void setEndStep(String endStep) {
    this.endStep = endStep;
  }

  @Override
  public String getCaptured() {
    return captured;
  }

  @Override
  public void setCaptured(String captured) {
    this.captured = captured;
  }

  @Override
  public Long getGameId() {
    return gameId;
  }

  @Override
  public void setGameId(Long gameId) {
    this.gameId = gameId;
  }

}
