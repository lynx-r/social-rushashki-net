package net.rushashki.shashki64.client.view;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 14:57
 */
public interface PlayView extends IsWidget {

  void setPlayer(String playerName);

  void setPresenter(Presenter presenter);

  public interface Presenter {
    void goTo(Place place);
  }

}
