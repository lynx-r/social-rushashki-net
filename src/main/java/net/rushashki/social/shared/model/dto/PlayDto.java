package net.rushashki.social.shared.model.dto;

import com.google.gwt.safehtml.shared.SafeHtml;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 25.11.14
 * Time: 18:07
 */
public class PlayDto {
  private String title;
  private String playerWhite;
  private String playerBlack;
  private SafeHtml deskHtml;

  public PlayDto() {
  }

  public PlayDto(String title, String playerWhite, String playerBlack) {
    this.title = title;
    this.playerWhite = playerWhite;
    this.playerBlack = playerBlack;
  }

  public PlayDto(String title, String playerWhite, String playerBlack, SafeHtml deskHtml) {
    this.title = title;
    this.playerWhite = playerWhite;
    this.playerBlack = playerBlack;
    this.deskHtml = deskHtml;
  }

  public String getPlayerWhite() {
    return playerWhite;
  }

  public void setPlayerWhite(String playerWhite) {
    this.playerWhite = playerWhite;
  }

  public String getPlayerBlack() {
    return playerBlack;
  }

  public void setPlayerBlack(String playerBlack) {
    this.playerBlack = playerBlack;
  }

  public SafeHtml getDeskHtml() {
    return deskHtml;
  }

  public void setDeskHtml(SafeHtml deskHtml) {
    this.deskHtml = deskHtml;
  }

  public String getBlackPlayer() {
    return playerBlack;
  }

  public String getWhitePlayer() {
    return playerWhite;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
