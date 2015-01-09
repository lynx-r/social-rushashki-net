package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 28.12.14
 * Time: 2:23
 */
public class OnRejectPlayEvent extends GwtEvent<OnRejectPlayEventHandler> {
  public static Type<OnRejectPlayEventHandler> TYPE = new Type<>();

  public Type<OnRejectPlayEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(OnRejectPlayEventHandler handler) {
    handler.onOnRejectPlay(this);
  }
}
