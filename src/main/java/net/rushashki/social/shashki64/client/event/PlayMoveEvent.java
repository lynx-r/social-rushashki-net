package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 03.01.15
 * Time: 17:11
 */
public class PlayMoveEvent extends GwtEvent<PlayMoveEventHandler> {

  public static Type<PlayMoveEventHandler> TYPE = new Type<>();
  private final String prevStep;
  private final String newStep;
  private final String captured;

  public PlayMoveEvent(String prevStep, String newStep, String captured) {
    this.prevStep = prevStep;
    this.newStep = newStep;
    this.captured = captured;
  }

  public String getStartMove() {
    return prevStep;
  }

  public String getEndMove() {
    return newStep;
  }

  public String getCaptured() {
    return captured;
  }

  public Type<PlayMoveEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(PlayMoveEventHandler handler) {
    handler.onPlayMove(this);
  }

}
