package net.rushashki.shashki64.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 18:11
 */
public interface NotationStrokeEventHandler extends EventHandler {
  void onNotationStroke(NotationStrokeEvent event);
}
