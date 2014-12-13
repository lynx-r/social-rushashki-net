package net.rushashki.social.client.event;

import com.google.gwt.event.shared.GwtEvent;
import net.rushashki.social.shared.model.Shashist;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 06.12.14
 * Time: 7:42
 */
public class OnGetProfileEvent extends GwtEvent<OnGetProfileEventHandler> {
  public static Type<OnGetProfileEventHandler> TYPE = new Type<OnGetProfileEventHandler>();

  private Shashist shashist;

  public OnGetProfileEvent(Shashist shashist) {
    this.shashist = shashist;
  }

  public Shashist getProfile() {
    return shashist;
  }

  public Type<OnGetProfileEventHandler> getAssociatedType() {
    return TYPE;
  }

  protected void dispatch(OnGetProfileEventHandler handler) {
    handler.onOnGetProfile(this);
  }
}
