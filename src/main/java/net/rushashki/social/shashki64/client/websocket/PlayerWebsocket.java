package net.rushashki.social.shashki64.client.websocket;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.websockets.client.WebSocket;
import com.google.gwt.websockets.client.WebSocketCallback;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.client.config.ShashkiGinjector;
import net.rushashki.social.shashki64.client.event.*;
import net.rushashki.social.shashki64.client.util.ShashkiLogger;
import net.rushashki.social.shashki64.shared.locale.ShashkiConstants;
import net.rushashki.social.shashki64.shared.model.Shashist;
import net.rushashki.social.shashki64.shared.websocket.message.MessageFactory;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessage;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessageImpl;

import java.util.List;
import java.util.logging.Level;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 07.12.14
 * Time: 11:39
 */
public class PlayerWebsocket implements WebSocketCallback {
  private final ShashkiLogger logger;
  private WebSocket webSocket;
  private ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  private ShashkiConstants constants;
  private EventBus eventBus;
  private Shashist player;
  private final String PLAYER_WEBSOCKET_URL = "ws://localhost:8080/ws/play";
  private ClientFactory clientFactory;

  public PlayerWebsocket(ClientFactory clientFactory) {
    this.clientFactory = clientFactory;
    this.player = clientFactory.getPlayer();
    this.constants = shashkiGinjector.getShashkiConstants();
    this.eventBus = shashkiGinjector.getEventBus();
    this.logger = shashkiGinjector.getLogger();

    eventBus.addHandler(OnConnectToPlayEvent.TYPE, event -> {
      webSocket = new WebSocket(this);
      webSocket.connect(PLAYER_WEBSOCKET_URL);
    });

    eventBus.addHandler(OnPlayerMessageEvent.TYPE, event -> {
      PlayerMessage playerMessage = event.getPlayerMessage();

      MessageFactory chatFactory = GWT.create(MessageFactory.class);
      AutoBean<PlayerMessage> bean = chatFactory.create(PlayerMessage.class, playerMessage);
      String message = AutoBeanCodex.encode(bean).getPayload();
      webSocket.send(message);
    });
  }

  private void handleUpdatePlayerList(List<Shashist> playerList) {
    clientFactory.setPlayerList(playerList);
    eventBus.fireEvent(new OnGetPlayerListEvent(playerList));
  }

  @Override
  public void onConnect() {
    PlayerMessage playerMessage = GWT.create(PlayerMessageImpl.class);
    playerMessage.setSender(player);
    playerMessage.setType(PlayerMessage.MessageType.PLAYER_REGISTER);

    MessageFactory chatFactory = GWT.create(MessageFactory.class);
    AutoBean<PlayerMessage> bean = chatFactory.create(PlayerMessage.class, playerMessage);
    String message = AutoBeanCodex.encode(bean).getPayload();
    webSocket.send(message);

    eventBus.fireEvent(new OnConnectedToPlayEvent());
  }

  @Override
  public void onDisconnect() {
    eventBus.fireEvent(new OnDisconnectFromPlayEvent());
  }

  @Override
  public void onMessage(String message) {
    MessageFactory messageFactory = GWT.create(MessageFactory.class);
    AutoBean<PlayerMessage> bean = AutoBeanCodex.decode(messageFactory, PlayerMessage.class, message);
    PlayerMessage playerMessage = bean.as();
    switch (playerMessage.getType()) {
      case USER_LIST_UPDATE:
        handleUpdatePlayerList(playerMessage.getPlayerList());
        break;
    }
  }
}
