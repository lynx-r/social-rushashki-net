package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 18:11
 */
public class OnNotationMoveEvent extends GwtEvent<OnNotationMoveEventHandler> {
  public static Type<OnNotationMoveEventHandler> TYPE = new Type<>();
  private final String stroke;

  public OnNotationMoveEvent(String stroke) {
    this.stroke = stroke;
  }

  public String getMove() {
    return stroke;
  }

  public Type<OnNotationMoveEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(OnNotationMoveEventHandler handler) {
    handler.onNotationStroke(this);
  }
}
