package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;
import net.rushashki.social.shashki64.client.ClientFactory;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 06.12.14
 * Time: 7:42
 */
public class ClientFactoryEvent extends GwtEvent<ClientFactoryEventHandler> {

  public static Type<ClientFactoryEventHandler> TYPE = new Type<>();

  private ClientFactory clientFactory;

  public ClientFactoryEvent(ClientFactory clientFactory) {
    this.clientFactory = clientFactory;
  }

  public ClientFactory getClientFactory() {
    return clientFactory;
  }

  public Type<ClientFactoryEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(ClientFactoryEventHandler handler) {
    handler.onOnClientFactory(this);
  }

}
