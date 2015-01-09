package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;
import net.rushashki.social.shashki64.shared.model.GameMessage;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 13.12.14
 * Time: 12:38
 */
public class OnGameMessageEvent extends GwtEvent<OnGameMessageEventHandler> {
  public static Type<OnGameMessageEventHandler> TYPE = new Type<>();
  private final GameMessage gameMessage;

  public OnGameMessageEvent(GameMessage gameMessage) {
    this.gameMessage = gameMessage;
  }

  public GameMessage getGameMessage() {
    return gameMessage;
  }

  public Type<OnGameMessageEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(OnGameMessageEventHandler handler) {
    handler.onOnPlayerMessage(this);
  }
}
