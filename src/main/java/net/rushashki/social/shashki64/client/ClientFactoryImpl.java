package net.rushashki.social.shashki64.client;

import net.rushashki.social.shashki64.shared.model.Shashist;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 21.12.14
 * Time: 12:29
 */
public class ClientFactoryImpl implements ClientFactory {

    private Shashist player;
    private List<Shashist> playerList;

    @Override
    public Shashist getPlayer() {
        return player;
    }

    @Override
    public void setPlayer(Shashist player) {
        this.player = player;
    }

    @Override
    public List<Shashist> getPlayerList() {
        return playerList;
    }

    @Override
    public void setPlayerList(List<Shashist> playerList) {
        this.playerList = playerList;
    }

}
