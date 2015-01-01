package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 15:07
 */
public class OnTurnEvent extends GwtEvent<OnTurnEventHandler> {
  public static Type<OnTurnEventHandler> TYPE = new Type<OnTurnEventHandler>();

  private boolean turn;

  public OnTurnEvent(boolean turn) {
    this.turn = turn;
  }

  public boolean isMyTurn() {
    return turn;
  }

  public Type<OnTurnEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(OnTurnEventHandler handler) {
    handler.onOnTurn(this);
  }
}
