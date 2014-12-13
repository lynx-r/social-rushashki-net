package net.rushashki.shashki64.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 13.12.14
 * Time: 12:38
 */
public interface OnWebsocketPlayerMessageEventHandler extends EventHandler {
  void onOnWebsocketPlayerMessage(OnWebsocketPlayerMessageEvent event);
}
