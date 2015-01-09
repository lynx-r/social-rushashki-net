package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 09.01.15
 * Time: 9:48
 */
public class OnTurnChangeEvent extends GwtEvent<OnTurnChangeEventHandler> {

  public static Type<OnTurnChangeEventHandler> TYPE = new Type<>();
  private boolean myTurn;

  public OnTurnChangeEvent(boolean myTurn) {
    this.myTurn = myTurn;
  }

  public boolean isMyTurn() {
    return myTurn;
  }

  public Type<OnTurnChangeEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(OnTurnChangeEventHandler handler) {
    handler.onOnTurnChange(this);
  }

}
