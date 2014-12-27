package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 28.12.14
 * Time: 1:57
 */
public class OnChatMessageEvent extends GwtEvent<OnChatMessageEventHandler> {
  public static Type<OnChatMessageEventHandler> TYPE = new Type<OnChatMessageEventHandler>();

  private String message;

  public OnChatMessageEvent(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public Type<OnChatMessageEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(OnChatMessageEventHandler handler) {
    handler.onOnChatMessage(this);
  }
}
