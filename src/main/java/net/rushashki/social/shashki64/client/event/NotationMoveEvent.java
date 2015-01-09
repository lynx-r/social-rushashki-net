package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 18:11
 */
public class NotationMoveEvent extends GwtEvent<NotationMoveEventHandler> {
  public static Type<NotationMoveEventHandler> TYPE = new Type<>();
  private final String stroke;

  public NotationMoveEvent(String stroke) {
    this.stroke = stroke;
  }

  public String getMove() {
    return stroke;
  }

  public Type<NotationMoveEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(NotationMoveEventHandler handler) {
    handler.onNotationMove(this);
  }
}
