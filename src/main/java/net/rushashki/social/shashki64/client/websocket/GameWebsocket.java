package net.rushashki.social.shashki64.client.websocket;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.websockets.client.WebSocket;
import com.google.gwt.websockets.client.WebSocketCallback;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.client.component.widget.dialog.ConfirmPlayDialogBox;
import net.rushashki.social.shashki64.client.component.widget.dialog.DialogBox;
import net.rushashki.social.shashki64.client.config.ShashkiGinjector;
import net.rushashki.social.shashki64.client.event.*;
import net.rushashki.social.shashki64.client.rpc.GameRpcServiceAsync;
import net.rushashki.social.shashki64.client.rpc.ProfileRpcServiceAsync;
import net.rushashki.social.shashki64.shared.dto.GameDto;
import net.rushashki.social.shashki64.shared.dto.GameMessageDto;
import net.rushashki.social.shashki64.shared.locale.ShashkiConstants;
import net.rushashki.social.shashki64.shared.model.Game;
import net.rushashki.social.shashki64.shared.model.GameMessage;
import net.rushashki.social.shashki64.shared.model.Message;
import net.rushashki.social.shashki64.shared.model.Shashist;
import net.rushashki.social.shashki64.shared.websocket.message.MessageFactory;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 07.12.14
 * Time: 11:39
 */
public class GameWebsocket implements WebSocketCallback {
  private final ProfileRpcServiceAsync profileService;
  private GameRpcServiceAsync gameService;
  private WebSocket webSocket;
  private EventBus eventBus;
  private Shashist player;
  private ShashkiConstants constants;
  private final String PLAYER_WEBSOCKET_URL = "ws://localhost:8080/ws/game";
  private ClientFactory clientFactory;

  public GameWebsocket(ClientFactory clientFactory) {
    ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;

    this.clientFactory = clientFactory;
    this.player = clientFactory.getPlayer();
    this.eventBus = shashkiGinjector.getEventBus();
    this.constants = shashkiGinjector.getShashkiConstants();
    this.gameService = shashkiGinjector.getGameService();
    this.profileService = shashkiGinjector.getProfileService();

    eventBus.addHandler(OnConnectToPlayEvent.TYPE, new OnConnectToPlayEventHandler() {
      @Override
      public void onOnConnectToPlay(OnConnectToPlayEvent event) {
        webSocket = new WebSocket(GameWebsocket.this);
        webSocket.connect(PLAYER_WEBSOCKET_URL);
      }
    });

    eventBus.addHandler(OnGameMessageEvent.TYPE, new OnGameMessageEventHandler() {
      @Override
      public void onOnPlayerMessage(OnGameMessageEvent event) {
        GameMessage gameMessage = event.getGameMessage();

        sendGameMessage(gameMessage);
      }
    });

    eventBus.addHandler(OnPlayMoveEvent.TYPE, new OnPlayMoveEventHandler() {
      @Override
      public void onOnPlayMove(OnPlayMoveEvent event) {
        GameMessage message = GWT.create(GameMessageDto.class);
        message.setSender(clientFactory.getPlayer());
        message.setReceiver(clientFactory.getOpponent());
        message.setMessageType(Message.MessageType.PLAY_MOVE);
        message.setStartMove(event.getStartMove());
        message.setEndMove(event.getEndMove());
        message.setCaptured(event.getCaptured());
        message.setGame(clientFactory.getGame());

        sendGameMessage(message);
      }
    });
  }

  private void sendGameMessage(GameMessage gameMessage) {
    MessageFactory chatFactory = GWT.create(MessageFactory.class);
    AutoBean<GameMessage> bean = chatFactory.create(GameMessage.class, gameMessage);
    String message = AutoBeanCodex.encode(bean).getPayload();
    webSocket.send(message);
  }

  private void handleUpdatePlayerList(List<Shashist> playerList) {
    clientFactory.setPlayerList(playerList);
    eventBus.fireEvent(new OnGetPlayerListEvent(playerList));
  }

  private void handlePlayInvite(GameMessage gameMessage) {
    ConfirmPlayDialogBox confirmPlayDialogBox = new ConfirmPlayDialogBox() {
      @Override
      public void submitted() {
        profileService.getProfile(gameMessage.getSender().getId(), new AsyncCallback<Shashist>() {
          @Override
          public void onFailure(Throwable caught) {
            new DialogBox(constants.error(), constants.errorWhileGettingProfile()).show();
          }

          @Override
          public void onSuccess(Shashist result) {
            clientFactory.setOpponent(result);

            Game game = GWT.create(GameDto.class);
            game.setPlayStartDate(new Date());
            game.setPlayerWhite(isWhite() ? clientFactory.getPlayer() : clientFactory.getOpponent());
            game.setPlayerBlack(isWhite() ? clientFactory.getOpponent() : clientFactory.getPlayer());
            gameService.createGame(game, new AsyncCallback<Game>() {
              @Override
              public void onFailure(Throwable throwable) {
                new DialogBox(constants.error(), constants.failedToStartGame() + throwable.getMessage()).show();
                throwable.printStackTrace();
              }

              @Override
              public void onSuccess(Game game) {
                GameMessage message = GWT.create(GameMessageDto.class);
                message.setSender(gameMessage.getReceiver());
                message.setReceiver(gameMessage.getSender());
                message.setMessageType(Message.MessageType.PLAY_START);

                message.setData(Boolean.TRUE.toString());
                message.setGame(game);

                sendGameMessage(message);

                clientFactory.setGame(game);
                eventBus.fireEvent(new OnStartPlayEvent(isWhite()));
              }
            });
          }
        });

      }

      @Override
      public void canceled() {
        GameMessage message = GWT.create(GameMessageDto.class);
        message.setSender(gameMessage.getReceiver());
        message.setReceiver(gameMessage.getSender());
        message.setMessageType(GameMessage.MessageType.PLAY_REJECT_INVITE);

        sendGameMessage(message);
      }
    };
    confirmPlayDialogBox.show(gameMessage.getMessage(), gameMessage.getSender(),
        Boolean.valueOf(gameMessage.getData()));
  }

  @Override
  public void onConnect() {
    GameMessage gameMessage = GWT.create(GameMessageDto.class);
    gameMessage.setSender(player);
    gameMessage.setMessageType(GameMessage.MessageType.PLAYER_REGISTER);

    sendGameMessage(gameMessage);

    clientFactory.setConnected(true);
    eventBus.fireEvent(new OnConnectedToPlayEvent());
  }

  @Override
  public void onDisconnect() {
    clientFactory.setConnected(false);
    eventBus.fireEvent(new OnDisconnectFromPlayEvent());
  }

  @Override
  public void onMessage(String message) {
    MessageFactory messageFactory = GWT.create(MessageFactory.class);
    AutoBean<GameMessage> bean = AutoBeanCodex.decode(messageFactory, GameMessage.class, message);
    GameMessage gameMessage = bean.as();
    switch (gameMessage.getMessageType()) {
      case USER_LIST_UPDATE:
        handleUpdatePlayerList(gameMessage.getPlayerList());
        break;
      case PLAY_INVITE:
        handlePlayInvite(gameMessage);
        break;
      case PLAY_START:
        handlePlayStart(gameMessage);
        break;
      case PLAY_REJECT_INVITE:
        handlePlayRejectInvite(gameMessage);
        break;
      case PLAY_MOVE:
        handlePlayMove(gameMessage);
        break;
      case CHAT_PRIVATE_MESSAGE:
        handleChatPrivateMessage(gameMessage);
        break;
    }
  }

  private void handlePlayMove(GameMessage gameMessage) {
    eventBus.fireEvent(new OnPlayMoveOpponentEvent(gameMessage.getStartStep(), gameMessage.getEndStep(),
        gameMessage.getCaptured()));
  }

  private void handlePlayStart(GameMessage gameMessage) {
    gameService.getGame(gameMessage.getGame().getId(), new AsyncCallback<Game>() {
      @Override
      public void onFailure(Throwable throwable) {
        new DialogBox(constants.error(), constants.errorWhileGettingGame()).show();
      }

      @Override
      public void onSuccess(Game game) {
        clientFactory.setGame(game);
        boolean white = Boolean.valueOf(gameMessage.getData());
        clientFactory.setOpponent(white ? game.getPlayerBlack() : game.getPlayerWhite());
        eventBus.fireEvent(new OnStartPlayEvent(white));
      }
    });
  }

  private void handleChatPrivateMessage(GameMessage gameMessage) {
    eventBus.fireEvent(new OnChatMessageEvent(gameMessage.getMessage()));
  }

  private void handlePlayRejectInvite(GameMessage gameMessage) {
    clientFactory.setOpponent(null);
    new DialogBox(constants.info(),
        constants.playerRejectedPlayRequest(gameMessage.getSender().getPublicName()))
        .show();
    eventBus.fireEvent(new OnRejectPlayEvent());
  }

}
