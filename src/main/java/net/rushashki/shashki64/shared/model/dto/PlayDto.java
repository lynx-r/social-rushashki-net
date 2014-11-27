package net.rushashki.shashki64.shared.model.dto;

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
  private String deskHtml;

//  public PlayDto() {}

//  public PlayDto(String title, String playerWhite, String playerBlack) {
//    this.title = title;
//    this.playerWhite = playerWhite;
//    this.playerBlack = playerBlack;
//  }
//
//  public PlayDto(String title, String playerWhite, String playerBlack, String deskHtml) {
//    this.title = title;
//    this.playerWhite = playerWhite;
//    this.playerBlack = playerBlack;
//    this.deskHtml = deskHtml;
//  }

  public String getDeskHtml() {
    return deskHtml;
  }

  public void setDeskHtml(String deskHtml) {
    this.deskHtml = deskHtml;
  }

  public String getBlackPlayer() {
    return playerBlack;
  }

  public void setPlayerBlack(String playerBlack) {
    this.playerBlack = playerBlack;
  }

  public String getWhitePlayer() {
    return playerWhite;
  }

  public void setPlayerWhite(String playerWhite) {
    this.playerWhite = playerWhite;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
