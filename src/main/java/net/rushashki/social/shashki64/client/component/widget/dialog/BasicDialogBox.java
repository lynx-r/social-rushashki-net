package net.rushashki.social.shashki64.client.component.widget.dialog;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.user.client.Event;
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
  protected int WIDTH = 400;
  protected int HEIGHT = 60;

  public BasicDialogBox() {
    ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
    constants = shashkiGinjector.getShashkiConstants();

    setAnimationEnabled(true);
    addHandler(keyPressEvent -> {
      if (keyPressEvent.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE) {
        hide();
      }
    }, KeyPressEvent.getType());
  }

  @Override
  protected void onPreviewNativeEvent(Event.NativePreviewEvent event) {
    super.onPreviewNativeEvent(event);
    switch (event.getTypeInt()) {
      case Event.ONKEYDOWN:
        if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE) {
          hide();
        }
        break;
    }
  }
}
