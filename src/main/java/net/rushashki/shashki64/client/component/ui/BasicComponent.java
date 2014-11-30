package net.rushashki.shashki64.client.component.ui;

import com.google.gwt.user.client.ui.Composite;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.shared.locale.ShashkiConstants;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 12:00
 */
public abstract class BasicComponent extends Composite {
  protected ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  protected ShashkiConstants constants;

  public BasicComponent() {
    this.constants = shashkiGinjector.getShashkiConstants();
  }
}
