package net.rushashki.social.shashki64.server.websocket.player.message;

import net.rushashki.social.shashki64.server.util.Util;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessage;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 01.10.14
 * Time: 23:16
 */
public class PlayerMessageDecoder implements Decoder.Text<PlayerMessage> {
  @Override
  public void init(EndpointConfig endpointConfig) {

  }

  @Override
  public void destroy() {

  }

  @Override
  public PlayerMessage decode(String s) throws DecodeException {
//    JsonObject jsonObject = Json.createReader(new StringReader(s)).readObject();

//    PlayerMessage playerMessage = new PlayerMessage();
//    playerMessage.setType(PlayerMessage.MessageType.valueOf(jsonObject.getString("type")));
//    playerMessage.setData(jsonObject.getString("data"));

//    playerMessage.setReceiver(jsonObject.getString("receiver"));
//    playerMessage.setSender(jsonObject.getString("sender"));
//    playerMessage.setMessage(jsonObject.getString("message", ""));


    try {
      return Util.deserializeFromJson(s);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public boolean willDecode(String s) {
    PlayerMessage playerMessage = Util.deserializeFromJson(s);
    return playerMessage.getType().equals(PlayerMessage.MessageType.CHAT_MESSAGE)
        || playerMessage.getType().equals(PlayerMessage.MessageType.CHAT_PRIVATE_MESSAGE)

        || playerMessage.getType().equals(PlayerMessage.MessageType.PLAYER_REGISTER)
        || playerMessage.getType().equals(PlayerMessage.MessageType.USER_LIST_UPDATE)

        || playerMessage.getType().equals(PlayerMessage.MessageType.PLAY_INVITE)
        || playerMessage.getType().equals(PlayerMessage.MessageType.PLAY_ACCEPT_INVITE)
        || playerMessage.getType().equals(PlayerMessage.MessageType.PLAY_START)
        || playerMessage.getType().equals(PlayerMessage.MessageType.PLAY_MOVE)
        || playerMessage.getType().equals(PlayerMessage.MessageType.PLAY_CANCEL_MOVE)
        || playerMessage.getType().equals(PlayerMessage.MessageType.PLAY_LEFT)
        || playerMessage.getType().equals(PlayerMessage.MessageType.PLAY_PROPOSE_DRAW)
        || playerMessage.getType().equals(PlayerMessage.MessageType.PLAY_ACCEPT_DRAW);
  }

}
