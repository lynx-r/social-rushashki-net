package net.rushashki.social.shashki64.server.websocket.game.message;

import net.rushashki.social.shashki64.server.util.Util;
import net.rushashki.social.shashki64.shared.model.GameMessage;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.util.Arrays;

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
    try {
      return Util.deserializeFromJson(s);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public boolean willDecode(String s) {
    GameMessage gameMessage = Util.deserializeFromJson(s);
    return Arrays.asList(GameMessage.MessageType.values()).contains(gameMessage.getMessageType());
  }

}
