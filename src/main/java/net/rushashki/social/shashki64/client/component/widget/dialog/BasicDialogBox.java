package net.rushashki.social.shashki64.client.component.widget.dialog;

import com.google.gwt.user.client.ui.DialogBox;
import net.rushashki.social.shashki64.client.config.ShashkiGinjector;
import net.rushashki.social.shashki64.shared.locale.ShashkiConstants;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 27.12.14
 * Time: 23:17
 */
public class BasicDialogBox extends DialogBox {
  protected ShashkiConstants constants;

  public BasicDialogBox() {
    ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
    constants = shashkiGinjector.getShashkiConstants();
  }
}
