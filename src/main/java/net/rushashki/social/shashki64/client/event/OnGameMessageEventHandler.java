package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 13.12.14
 * Time: 12:38
 */
public interface OnGameMessageEventHandler extends EventHandler {
  void onOnPlayerMessage(OnGameMessageEvent event);
}
