package net.rushashki.social.shashki64.shared.dto;

import net.rushashki.social.shashki64.shared.model.Game;
import net.rushashki.social.shashki64.shared.model.Shashist;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 31.12.14
 * Time: 19:06
 */
public class GameDto implements Game {

  private Shashist playerBlack;
  private Shashist playerWhite;
  private GameEnds playEndStatus;
  private Date playStartDate;
  private Date playEndDate;
  private String partyNotation;

  public GameDto() {
  }

  @Override
  public Shashist getPlayerBlack() {
    return playerBlack;
  }

  @Override
  public void setPlayerBlack(Shashist playerBlack) {
    this.playerBlack = playerBlack;
  }

  @Override
  public Shashist getPlayerWhite() {
    return playerWhite;
  }

  @Override
  public void setPlayerWhite(Shashist playerWhite) {
    this.playerWhite = playerWhite;
  }

  @Override
  public GameEnds getPlayEndStatus() {
    return playEndStatus;
  }

  @Override
  public void setPlayEndStatus(GameEnds playEndStatus) {
    this.playEndStatus = playEndStatus;
  }

  @Override
  public Date getPlayStartDate() {
    return playStartDate;
  }

  @Override
  public void setPlayStartDate(Date playStartDate) {
    this.playStartDate = playStartDate;
  }

  @Override
  public Date getPlayEndDate() {
    return playEndDate;
  }

  @Override
  public void setPlayEndDate(Date playEndDate) {
    this.playEndDate = playEndDate;
  }

  @Override
  public String getPartyNotation() {
    return partyNotation;
  }

  @Override
  public void setPartyNotation(String partyNotation) {
    this.partyNotation = partyNotation;
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
