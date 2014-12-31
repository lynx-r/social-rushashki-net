package net.rushashki.social.shashki64.shared.model;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 31.12.14
 * Time: 23:23
 */
public interface GameProxy extends PersistableObject  {

  Shashist getPlayerBlack();

  void setPlayerBlack(Shashist playerBlack);

  Shashist getPlayerWhite();

  void setPlayerWhite(Shashist playerWhite);

  public enum GameEnds {
    BLACK_WON,
    WHITE_WON,
    BLACK_LEFT,
    WHITE_LEFT,
    SURRENDER_BLACK,
    SURRENDER_WHITE,
    DRAW
  }

}
