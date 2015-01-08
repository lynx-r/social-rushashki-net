package net.rushashki.social.shashki64.client;

import net.rushashki.social.shashki64.shared.model.Game;
import net.rushashki.social.shashki64.shared.model.Shashist;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 21.12.14
 * Time: 12:28
 */
public interface ClientFactory {

    Shashist getPlayer();

    void setPlayer(Shashist player);

    List<Shashist> getPlayerList();

    void setPlayerList(List<Shashist> playerList);

    Shashist getOpponent();

    void setOpponent(Shashist opponent);

    Game getGame();

    void setGame(Game game);

    boolean isConnected();

    void setConnected(boolean connected);

}
