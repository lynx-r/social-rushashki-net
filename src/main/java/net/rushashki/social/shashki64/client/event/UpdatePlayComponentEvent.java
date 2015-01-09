package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 09.01.15
 * Time: 22:03
 */
public class UpdatePlayComponentEvent extends GwtEvent<UpdatePlayComponentEventHandler> {
  public static Type<UpdatePlayComponentEventHandler> TYPE = new Type<UpdatePlayComponentEventHandler>();

  public Type<UpdatePlayComponentEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(UpdatePlayComponentEventHandler handler) {
    handler.onUpdatePlayComponent(this);
  }
}
