package net.rushashki.social.shashki64.server.websocket.game.message;

import net.rushashki.social.shashki64.server.util.Utils;
import net.rushashki.social.shashki64.shared.model.GameMessage;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 01.10.14
 * Time: 23:16
 */
public class GameMessageDecoder implements Decoder.Text<GameMessage> {
  @Override
  public void init(EndpointConfig endpointConfig) {

  }

  @Override
  public void destroy() {

  }

  @Override
  public GameMessage decode(String s) throws DecodeException {
//    JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();

//    PlayerMessage gameMessage = new PlayerMessage();
//    gameMessage.setMessageType(PlayerMessage.MessageType.valueOf(jsonObject.getString("type")));
//    gameMessage.setData(jsonObject.getString("data"));

//    gameMessage.setReceiver(jsonObject.getString("receiver"));
//    gameMessage.setSender(jsonObject.getString("sender"));
//    gameMessage.setMessage(jsonObject.getString("message", ""));


    try {
      return Utils.deserializeFromJson(s);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public boolean willDecode(String s) {
    GameMessage gameMessage = Utils.deserializeFromJson(s);
    return gameMessage.getMessageType().equals(GameMessage.MessageType.CHAT_MESSAGE)
        || gameMessage.getMessageType().equals(GameMessage.MessageType.CHAT_PRIVATE_MESSAGE)

        || gameMessage.getMessageType().equals(GameMessage.MessageType.PLAYER_REGISTER)
        || gameMessage.getMessageType().equals(GameMessage.MessageType.USER_LIST_UPDATE)

        || gameMessage.getMessageType().equals(GameMessage.MessageType.PLAY_INVITE)
        || gameMessage.getMessageType().equals(GameMessage.MessageType.PLAY_REJECT_INVITE)
        || gameMessage.getMessageType().equals(GameMessage.MessageType.PLAY_START)
        || gameMessage.getMessageType().equals(GameMessage.MessageType.PLAY_MOVE)
        || gameMessage.getMessageType().equals(GameMessage.MessageType.PLAY_CANCEL_MOVE)
        || gameMessage.getMessageType().equals(GameMessage.MessageType.PLAY_LEFT)
        || gameMessage.getMessageType().equals(GameMessage.MessageType.PLAY_PROPOSE_DRAW)
        || gameMessage.getMessageType().equals(GameMessage.MessageType.PLAY_ACCEPT_DRAW);
  }

}
