package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessage;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 13.12.14
 * Time: 12:38
 */
public class OnPlayerMessageEvent extends GwtEvent<OnPlayerMessageEventHandler> {
  public static Type<OnPlayerMessageEventHandler> TYPE = new Type<OnPlayerMessageEventHandler>();
  private final PlayerMessage playerMessage;

  public OnPlayerMessageEvent(PlayerMessage playerMessage) {
    this.playerMessage = playerMessage;
  }

  public PlayerMessage getPlayerMessage() {
    return playerMessage;
  }

  public Type<OnPlayerMessageEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(OnPlayerMessageEventHandler handler) {
    handler.onOnPlayerMessage(this);
  }
}
