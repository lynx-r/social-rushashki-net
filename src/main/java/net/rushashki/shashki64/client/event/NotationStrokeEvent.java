package net.rushashki.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 18:11
 */
public class NotationStrokeEvent extends GwtEvent<NotationStrokeEventHandler> {
  public static Type<NotationStrokeEventHandler> TYPE = new Type<NotationStrokeEventHandler>();
  private final String stroke;

  public NotationStrokeEvent(String stroke) {
    this.stroke = stroke;
  }

  public String getStroke() {
    return stroke;
  }

  public Type<NotationStrokeEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(NotationStrokeEventHandler handler) {
    handler.onNotationStroke(this);
  }
}
