package net.rushashki.social.shashki64.shared.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 31.12.14
 * Time: 15:47
 */
public interface GameMessage extends Message {

  String getStartStep();

  void setStartMove(String startStep);

  String getEndStep();

  void setEndMove(String endStep);

  String getCaptured();

  void setCaptured(String captured);

  Game getGame();

  void setGame(Game gameId);

  public List<Shashist> getPlayerList();

  public void setPlayerList(List<Shashist> playerList);

}
