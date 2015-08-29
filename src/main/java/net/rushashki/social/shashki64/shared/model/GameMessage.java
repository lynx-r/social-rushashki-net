package net.rushashki.social.shashki64.shared.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 31.12.14
 * Time: 15:47
 */
public interface GameMessage extends Message {

  String getStartMove();

  void setStartMove(String startStep);

  String getEndMove();

  void setEndMove(String endStep);

  String getCaptured();

  void setCaptured(String captured);

  Game getGame();

  void setGame(Game gameId);

  List<Shashist> getPlayerList();

  void setPlayerList(List<Shashist> playerList);
}
