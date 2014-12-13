package net.rushashki.social.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 18:11
 */
public class OnNotationStrokeEvent extends GwtEvent<OnNotationStrokeEventHandler> {
  public static Type<OnNotationStrokeEventHandler> TYPE = new Type<OnNotationStrokeEventHandler>();
  private final String stroke;

  public OnNotationStrokeEvent(String stroke) {
    this.stroke = stroke;
  }

  public String getStroke() {
    return stroke;
  }

  public Type<OnNotationStrokeEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(OnNotationStrokeEventHandler handler) {
    handler.onNotationStroke(this);
  }
}
