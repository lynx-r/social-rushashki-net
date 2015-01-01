package net.rushashki.social.shashki64.shared.dto;

import net.rushashki.social.shashki64.shared.model.Shashist;
import net.rushashki.social.shashki64.shared.model.GameMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 07.12.14
 * Time: 18:56
 */
public class GameMessageDto implements GameMessage {

  private Shashist sender;
  private Shashist receiver;
  private String message;
  private MessageType type;
  private List<Shashist> playerList = new ArrayList<>();
  private String data;
  private Date sentDate;
  private String startStep;
  private String endStep;
  private String captured;
  private Long gameId;

  public GameMessageDto() {
  }

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
  public Date getSentDate() {
    return sentDate;
  }

  @Override
  public void setSentDate(Date sentDate) {
    this.sentDate = sentDate;
  }

  @Override
  public MessageType getMessageType() {
    return type;
  }

  @Override
  public void setMessageType(MessageType messageType) {
    this.type = messageType;
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

  @Override
  public List<Shashist> getPlayerList() {
    return playerList;
  }

  @Override
  public void setPlayerList(List<Shashist> playerList) {
    this.playerList = playerList;
  }

  @Override
  public Long getId() {
    return null;
  }

  @Override
  public Integer getVersion() {
    return null;
  }

}
