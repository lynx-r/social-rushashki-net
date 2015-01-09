package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 18:11
 */
public interface OnNotationMoveEventHandler extends EventHandler {
  void onNotationStroke(OnNotationMoveEvent event);
}
