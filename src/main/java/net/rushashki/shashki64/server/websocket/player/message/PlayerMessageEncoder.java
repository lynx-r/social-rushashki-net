package net.rushashki.shashki64.server.websocket.player.message;

import net.rushashki.shashki64.server.util.Util;
import net.rushashki.shashki64.shared.websocket.message.PlayerMessage;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 01.10.14
 * Time: 23:20
 */
public class PlayerMessageEncoder implements Encoder.Text<PlayerMessage> {
  @Override
  public String encode(PlayerMessage playerMessage) throws EncodeException {
    return Util.serializePlayerMessageToJson(playerMessage);
  }

  @Override
  public void init(EndpointConfig endpointConfig) {

  }

  @Override
  public void destroy() {

  }

}
