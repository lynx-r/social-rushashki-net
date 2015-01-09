package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 03.01.15
 * Time: 17:11
 */
public class OnPlayMoveEvent extends GwtEvent<OnPlayMoveEventHandler> {

  public static Type<OnPlayMoveEventHandler> TYPE = new Type<>();
  private final String prevStep;
  private final String newStep;
  private final String captured;

  public OnPlayMoveEvent(String prevStep, String newStep, String captured) {
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

  public Type<OnPlayMoveEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(OnPlayMoveEventHandler handler) {
    handler.onOnPlayMove(this);
  }

}
