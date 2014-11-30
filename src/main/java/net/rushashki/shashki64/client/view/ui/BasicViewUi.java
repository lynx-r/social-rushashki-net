package net.rushashki.shashki64.client.view.ui;

import com.google.gwt.user.client.ui.Composite;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.client.util.ShashkiLogger;
import net.rushashki.shashki64.client.view.BasicView;
import net.rushashki.shashki64.shared.locale.ShashkiConstants;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 24.11.14
 * Time: 23:02
 */
public class BasicViewUi extends Composite {

  protected final ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;

  protected final ShashkiLogger logger;
  protected final ShashkiConstants constants;

  protected String token;
  protected BasicView.Presenter presenter;

  public BasicViewUi() {
    logger = shashkiGinjector.getLogger();
    constants = shashkiGinjector.getShashkiConstants();
  }

}
