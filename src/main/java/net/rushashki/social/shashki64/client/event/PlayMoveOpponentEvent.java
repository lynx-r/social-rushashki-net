package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 03.01.15
 * Time: 19:46
 */
public class PlayMoveOpponentEvent extends GwtEvent<PlayMoveOpponentEventHandler> {
  public static Type<PlayMoveOpponentEventHandler> TYPE = new Type<>();
  private final String startMove;
  private final String endMove;
  private final String captured;

  public PlayMoveOpponentEvent(String startMove, String endMove, String captured) {
    this.startMove = startMove;
    this.endMove = endMove;
    this.captured = captured;
  }

  public String getStartMove() {
    return startMove;
  }

  public String getEndMove() {
    return endMove;
  }

  public String getCaptured() {
    return captured;
  }

  public Type<PlayMoveOpponentEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(PlayMoveOpponentEventHandler handler) {
    handler.onOnPlayMoveOpponent(this);
  }
}
