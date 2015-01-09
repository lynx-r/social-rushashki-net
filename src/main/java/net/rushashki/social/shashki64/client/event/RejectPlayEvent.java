package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 28.12.14
 * Time: 2:23
 */
public class RejectPlayEvent extends GwtEvent<RejectPlayEventHandler> {
  public static Type<RejectPlayEventHandler> TYPE = new Type<>();

  public Type<RejectPlayEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(RejectPlayEventHandler handler) {
    handler.onOnRejectPlay(this);
  }
}
