package net.rushashki.shashki64.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import net.rushashki.shashki64.share.resources.Resources;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 14.11.14
 * Time: 0:11
 */
public class Shashki64 implements EntryPoint {

  private Image splashImage;


  public void onModuleLoad() {
    splashImage = new Image(Resources.INSTANCE.images().loadIconImage().getSafeUri());
    splashImage.addStyleName("loader-image");
    RootPanel.get("content").add(splashImage);


  }
}
