package net.rushashki.social.shashki64.client.component.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.social.shashki64.client.config.ShashkiGinjector;
import net.rushashki.social.shashki64.client.rpc.GameMessageRpcServiceAsync;
import net.rushashki.social.shashki64.shared.locale.ShashkiConstants;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 12:00
 */
public abstract class BasicComponent extends Composite {
  protected final EventBus eventBus;
  protected final ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  protected final ShashkiConstants constants;
  protected final GameMessageRpcServiceAsync gameMessageService;

  public BasicComponent() {
    this.constants = shashkiGinjector.getShashkiConstants();
    this.eventBus = shashkiGinjector.getEventBus();
    this.gameMessageService = shashkiGinjector.getGameMessageService();
  }
}
