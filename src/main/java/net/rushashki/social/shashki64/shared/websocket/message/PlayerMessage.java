package net.rushashki.social.shashki64.shared.websocket.message;

import net.rushashki.social.shashki64.shared.model.Shashist;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 07.12.14
 * Time: 16:01
 */
public interface PlayerMessage extends Message {

  public MessageType getType();

  public void setType(MessageType type);

  public List<Shashist> getPlayerList();

  public void setPlayerList(List<Shashist> playerList);

  public enum MessageType {

    CHAT_MESSAGE,
    USER_LIST_UPDATE,
    PLAY_INVITE,
    PLAY_ACCEPT_INVITE,
    PLAY_START,
    PLAY_MOVE,
    PLAY_CANCEL_MOVE,
    PLAY_LEFT,
    PLAY_PROPOSE_DRAW,
    PLAY_ACCEPT_DRAW,
    PLAYER_REGISTER,
    PLAYER_DISCONNECT,
    PLAYER_OFFLINE;

  }

}
