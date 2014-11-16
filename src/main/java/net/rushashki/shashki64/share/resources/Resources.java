package net.rushashki.shashki64.share.resources;

import com.google.gwt.core.client.GWT;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 16.11.14
 * Time: 1:02
 */
public class Resources {
  public static Bundle INSTANCE = GWT.create(Bundle.class);
  static
  {
    INSTANCE.css().ensureInjected();
  }
}
