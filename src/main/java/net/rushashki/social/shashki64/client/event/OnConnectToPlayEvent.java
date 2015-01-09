package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 27.12.14
 * Time: 10:48
 */
public class OnConnectToPlayEvent extends GwtEvent<OnConnectToPlayEventHandler> {
    public static Type<OnConnectToPlayEventHandler> TYPE = new Type<>();

    public Type<OnConnectToPlayEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(OnConnectToPlayEventHandler handler) {
        handler.onOnConnectToPlay(this);
    }
}
