package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 13.01.15
 * Time: 0:32
 */
public interface WebsocketReconnectEventHandler extends EventHandler {
  void onWebsocketReconnect(WebsocketReconnectEvent event);
}
