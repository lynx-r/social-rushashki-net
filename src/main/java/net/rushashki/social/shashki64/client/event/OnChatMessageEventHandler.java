package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 28.12.14
 * Time: 1:57
 */
public interface OnChatMessageEventHandler extends EventHandler {
  void onOnChatMessage(OnChatMessageEvent event);
}
