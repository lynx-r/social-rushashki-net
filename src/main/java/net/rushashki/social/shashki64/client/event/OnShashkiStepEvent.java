package net.rushashki.social.shashki64.client.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 03.01.15
 * Time: 17:11
 */
public class OnShashkiStepEvent extends GwtEvent<OnShashkiStepEventHandler> {

  public static Type<OnShashkiStepEventHandler> TYPE = new Type<OnShashkiStepEventHandler>();
  private final String prevStep;
  private final String newStep;
  private final String captured;

  public OnShashkiStepEvent(String prevStep, String newStep, String captured) {
    this.prevStep = prevStep;
    this.newStep = newStep;
    this.captured = captured;
  }

  public String getPrevStep() {
    return prevStep;
  }

  public String getNewStep() {
    return newStep;
  }

  public String getCaptured() {
    return captured;
  }

  public Type<OnShashkiStepEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(OnShashkiStepEventHandler handler) {
    handler.onOnShashkiStep(this);
  }

}
