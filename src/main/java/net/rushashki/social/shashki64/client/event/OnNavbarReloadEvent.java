package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 23:31
 */
public class OnNavbarReloadEvent extends GwtEvent<OnNavbarReloadEventHandler> {
  public static Type<OnNavbarReloadEventHandler> TYPE = new Type<>();
  private String token;

  public OnNavbarReloadEvent(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  @Override
  public Type<OnNavbarReloadEventHandler> getAssociatedType() {
    return TYPE;
  }

  @Override
  protected void dispatch(OnNavbarReloadEventHandler handler) {
    handler.onEvent(this);
  }
}
