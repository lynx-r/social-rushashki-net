package net.rushashki.shashki64.client.view.ui;

import com.google.gwt.user.client.ui.Composite;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.client.util.ShashkiLogger;
import net.rushashki.shashki64.client.view.BasicView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 24.11.14
 * Time: 23:02
 */
public class BasicViewUi extends Composite {

  protected ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;

  protected ShashkiLogger logger;

  protected String token;
  protected BasicView.Presenter presenter;

  public BasicViewUi() {
    logger = shashkiGinjector.getLogger();
  }

}
