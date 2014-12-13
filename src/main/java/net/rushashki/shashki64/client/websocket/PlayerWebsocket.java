package net.rushashki.shashki64.client.websocket;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.client.event.OnGetPlayerListEvent;
import net.rushashki.shashki64.client.util.ShashkiLogger;
import net.rushashki.shashki64.shared.locale.ShashkiConstants;
import net.rushashki.shashki64.shared.model.Shashist;
import net.rushashki.shashki64.shared.websocket.message.MessageFactory;
import net.rushashki.shashki64.shared.websocket.message.PlayerMessage;
import net.rushashki.shashki64.shared.websocket.message.PlayerMessageImpl;
import org.realityforge.gwt.websockets.client.WebSocket;
import org.realityforge.gwt.websockets.client.WebSocketListenerAdapter;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 07.12.14
 * Time: 11:39
 */
public class PlayerWebSocket extends WebSocketListenerAdapter {
  private final ShashkiLogger logger;
  private WebSocket webSocket;
  private ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  private ShashkiConstants constants;
  private EventBus eventBus;
  private Shashist player;
  private final String PLAYER_WEBSOCKET_URL = "ws://localhost:8080/ws/echo";

  public PlayerWebSocket(Shashist shashist) {
    this.player = shashist;
    this.webSocket = WebSocket.newWebSocketIfSupported();
    this.constants = shashkiGinjector.getShashkiConstants();
    this.eventBus = shashkiGinjector.getEventBus();
    this.logger = shashkiGinjector.getLogger();

    if (webSocket == null) {
      Window.alert(constants.webSocketDoesNotSupport());
      return;
    }

    webSocket.setListener(this);
    webSocket.connect(PLAYER_WEBSOCKET_URL);
  }

  @Override
  public void onOpen(@Nonnull WebSocket webSocket) {
    PlayerMessage playerMessage = GWT.create(PlayerMessageImpl.class);
    try {
      playerMessage.setSender(player);
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Invalid uid");
      return;
    }
    playerMessage.setType(PlayerMessage.MessageType.REGISTER_PLAYER);

    MessageFactory chatFactory = GWT.create(MessageFactory.class);
    AutoBean<PlayerMessage> bean = chatFactory.create(PlayerMessage.class, playerMessage);
    String message = AutoBeanCodex.encode(bean).getPayload();
    webSocket.send(message);
  }

  @Override
  public void onMessage(@Nonnull WebSocket webSocket, @Nonnull String data) {
    MessageFactory messageFactory = GWT.create(MessageFactory.class);
    AutoBean<PlayerMessage> bean = AutoBeanCodex.decode(messageFactory, PlayerMessage.class, data);
    PlayerMessage playerMessage = bean.as();
    switch (playerMessage.getType()) {
      case USER_LIST_UPDATE:
        handleUpdatePlayerList(playerMessage.getPlayerList());
        break;
    }
  }

  private void handleUpdatePlayerList(List<Shashist> playerList) {
    eventBus.fireEvent(new OnGetPlayerListEvent(playerList));
  }

  @Override
  public void onClose(@Nonnull WebSocket webSocket, boolean wasClean, int code, String reason) {
    PlayerMessage playerMessage = GWT.create(PlayerMessageImpl.class);
    try {
      playerMessage.setSender(player);
    } catch (Exception e) {
      return;
    }
    playerMessage.setType(PlayerMessage.MessageType.DISCONNECT_PLAYER);
    MessageFactory chatFactory = GWT.create(MessageFactory.class);
    AutoBean<PlayerMessage> bean = chatFactory.create(PlayerMessage.class, playerMessage);
    String message = AutoBeanCodex.encode(bean).getPayload();
    webSocket.send(message);
  }
}
