package net.rushashki.shashki64.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import net.rushashki.shashki64.client.place.PlayPlace;
import net.rushashki.shashki64.client.view.PlayView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 15:07
 */
public class PlayActivity extends AbstractActivity implements PlayView.Presenter {

  private PlayView playView;

  private PlaceController placeController;

  private String name;

  public PlayActivity(PlayPlace playPlace, PlayView playView, PlaceController placeController) {
    this.name = playPlace.getPlayerName();
    this.playView = playView;
    this.placeController = placeController;
  }

  @Override
  public void start(AcceptsOneWidget panel, EventBus eventBus) {
    playView.setPlayer(name);
    playView.setPresenter(this);
    panel.setWidget(playView.asWidget());
  }

  @Override
  public void goTo(Place place) {
    placeController.goTo(place);
  }
}
