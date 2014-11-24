package net.rushashki.shashki64.shared.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import net.rushashki.shashki64.shared.resources.images.Images;
import net.rushashki.shashki64.shared.resources.sounds.Sounds;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 16.11.14
 * Time: 1:03
 */
public interface Bundle extends ClientBundle {
  public Images images();
  public Sounds sounds();

  @CssResource.NotStrict
  @ClientBundle.Source("css/base.css")
  CssResource css();

}
