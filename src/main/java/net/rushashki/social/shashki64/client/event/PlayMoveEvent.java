package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;
import net.rushashki.social.shashki64.shashki.dto.MoveDto;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 03.01.15
 * Time: 17:11
 */
public class PlayMoveEvent extends GwtEvent<PlayMoveEventHandler> {

  public static Type<PlayMoveEventHandler> TYPE = new Type<>();
  private final MoveDto move;

  public PlayMoveEvent(MoveDto move) {
    this.move = move;
  }

  public MoveDto getMove() {
    return move;
  }

  public Type<PlayMoveEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(PlayMoveEventHandler handler) {
    handler.onPlayMove(this);
  }

}
