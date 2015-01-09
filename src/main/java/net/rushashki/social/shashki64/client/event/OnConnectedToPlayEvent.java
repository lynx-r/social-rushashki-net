package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 27.12.14
 * Time: 10:50
 */
public class OnConnectedToPlayEvent extends GwtEvent<OnConnectedToPlayEventHandler> {
    public static Type<OnConnectedToPlayEventHandler> TYPE = new Type<>();

    public Type<OnConnectedToPlayEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(OnConnectedToPlayEventHandler handler) {
        handler.onOnConnectedToPlay(this);
    }
}
