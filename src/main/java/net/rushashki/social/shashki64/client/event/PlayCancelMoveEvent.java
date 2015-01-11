package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 11.01.15
 * Time: 19:40
 */
public class PlayCancelMoveEvent extends GwtEvent<PlayCancelMoveEventHandler> {
  public static Type<PlayCancelMoveEventHandler> TYPE = new Type<PlayCancelMoveEventHandler>();

  public Type<PlayCancelMoveEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(PlayCancelMoveEventHandler handler) {
    handler.onPlayCancelMove(this);
  }
}
