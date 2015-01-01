package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;
import net.rushashki.social.shashki64.client.ClientFactory;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 06.12.14
 * Time: 7:42
 */
public class OnClientFactoryEvent extends GwtEvent<OnClientFactoryEventHandler> {

  public static Type<OnClientFactoryEventHandler> TYPE = new Type<OnClientFactoryEventHandler>();

  private ClientFactory clientFactory;

  public OnClientFactoryEvent(ClientFactory clientFactory) {
    this.clientFactory = clientFactory;
  }

  public ClientFactory getClientFactory() {
    return clientFactory;
  }

  public Type<OnClientFactoryEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(OnClientFactoryEventHandler handler) {
    handler.onOnClientFactory(this);
  }

}
