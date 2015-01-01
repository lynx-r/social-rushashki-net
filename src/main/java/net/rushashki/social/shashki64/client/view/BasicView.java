package net.rushashki.social.shashki64.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.shared.model.Shashist;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 24.11.14
 * Time: 16:09
 */
public interface BasicView extends IsWidget {

  ClientFactory getClientFactory();

  void setClientFactory(ClientFactory clientFactory);

  void setToken(String token);

  void setPresenter(Presenter presenter);

  public interface Presenter {
    void goTo(Place place);
  }

}
