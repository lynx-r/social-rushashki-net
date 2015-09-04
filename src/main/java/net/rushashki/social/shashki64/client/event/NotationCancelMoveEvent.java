package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 01.09.15
 * Time: 19:49
 */
public class NotationCancelMoveEvent extends GwtEvent<NotationCancelMoveEventHandler> {
  public static Type<NotationCancelMoveEventHandler> TYPE = new Type<NotationCancelMoveEventHandler>();

  public Type<NotationCancelMoveEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(NotationCancelMoveEventHandler handler) {
    handler.onNotationCancelMove(this);
  }
}
