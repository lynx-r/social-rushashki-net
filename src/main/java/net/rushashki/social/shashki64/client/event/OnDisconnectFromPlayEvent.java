package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 27.12.14
 * Time: 10:51
 */
public class OnDisconnectFromPlayEvent extends GwtEvent<OnDisconnectFromPlayEventHandler> {
    public static Type<OnDisconnectFromPlayEventHandler> TYPE = new Type<OnDisconnectFromPlayEventHandler>();

    public Type<OnDisconnectFromPlayEventHandler> getAssociatedType() {
        return TYPE;
    }

    protected void dispatch(OnDisconnectFromPlayEventHandler handler) {
        handler.onOnDisconnectFromPlay(this);
    }
}
