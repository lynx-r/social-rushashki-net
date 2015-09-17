package net.rushashki.social.shashki64.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 24.11.14
 * Time: 16:09
 */
public interface BasicView extends IsWidget {

  void setToken(String token);

  void setPresenter(Presenter presenter);

  interface Presenter {
    void goTo(Place place);
  }
}
