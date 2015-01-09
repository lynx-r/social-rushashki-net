package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;
import net.rushashki.social.shashki64.shared.model.Shashist;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 11.12.14
 * Time: 0:41
 */
public class OnGetPlayerListEvent extends GwtEvent<OnGetPlayerListEventHandler> {
  public static Type<OnGetPlayerListEventHandler> TYPE = new Type<>();
  private List<Shashist> playerList;

  public OnGetPlayerListEvent(List<Shashist> playerList) {
    this.playerList = playerList;
  }

  public List<Shashist> getPlayerList() {
    return playerList;
  }

  public Type<OnGetPlayerListEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(OnGetPlayerListEventHandler handler) {
    handler.onOnGetPlayerList(this);
  }
}
