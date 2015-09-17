package net.rushashki.social.shashki64.client.view.ui;

import com.google.gwt.user.client.ui.Composite;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.client.config.ShashkiGinjector;
import net.rushashki.social.shashki64.client.rpc.ProfileRpcServiceAsync;
import net.rushashki.social.shashki64.client.util.ShashkiLogger;
import net.rushashki.social.shashki64.client.view.BasicView;
import net.rushashki.social.shashki64.shared.locale.ShashkiConstants;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 24.11.14
 * Time: 23:02
 */
public abstract class BasicViewUi extends Composite implements BasicView {

  protected final ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;

  protected final ShashkiLogger logger;
  protected final ShashkiConstants constants;

  protected String token;
  protected BasicView.Presenter presenter;
  protected ProfileRpcServiceAsync profileService;
  protected ClientFactory clientFactory;

  public BasicViewUi() {
    logger = shashkiGinjector.getLogger();
    constants = shashkiGinjector.getShashkiConstants();
    profileService = shashkiGinjector.getProfileService();
    clientFactory = shashkiGinjector.getClientFactory();
  }

  @Override
  public abstract void setToken(String token);

  @Override
  public abstract void setPresenter(Presenter presenter);

}
