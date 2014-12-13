package net.rushashki.social.client.event;

import com.google.gwt.event.shared.GwtEvent;
import net.rushashki.social.shared.websocket.message.PlayerMessage;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 13.12.14
 * Time: 12:38
 */
public class OnWebsocketPlayerMessageEvent extends GwtEvent<OnWebsocketPlayerMessageEventHandler> {
  public static Type<OnWebsocketPlayerMessageEventHandler> TYPE = new Type<OnWebsocketPlayerMessageEventHandler>();
  private final PlayerMessage playerMessage;

  public OnWebsocketPlayerMessageEvent(PlayerMessage playerMessage) {
    this.playerMessage = playerMessage;
  }

  public PlayerMessage getPlayerMessage() {
    return playerMessage;
  }

  public Type<OnWebsocketPlayerMessageEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(OnWebsocketPlayerMessageEventHandler handler) {
    handler.onOnWebsocketPlayerMessage(this);
  }
}
