package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 28.12.14
 * Time: 1:37
 */
public class OnStartPlayEvent extends GwtEvent<OnStartPlayEventHandler> {
  public static Type<OnStartPlayEventHandler> TYPE = new Type<OnStartPlayEventHandler>();

  public Type<OnStartPlayEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(OnStartPlayEventHandler handler) {
    handler.onOnStartPlay(this);
  }
}
