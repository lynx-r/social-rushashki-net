package net.rushashki.social.shashki64.client.websocket;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.websockets.client.WebSocket;
import com.google.gwt.websockets.client.WebSocketCallback;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.client.component.widget.dialog.ConfirmeDialogBox;
import net.rushashki.social.shashki64.client.component.widget.dialog.ConfirmPlayDialogBox;
import net.rushashki.social.shashki64.client.component.widget.dialog.DialogBox;
import net.rushashki.social.shashki64.client.config.ShashkiGinjector;
import net.rushashki.social.shashki64.client.event.*;
import net.rushashki.social.shashki64.client.rpc.GameRpcServiceAsync;
import net.rushashki.social.shashki64.client.rpc.ProfileRpcServiceAsync;
import net.rushashki.social.shashki64.shared.config.ShashkiConfiguration;
import net.rushashki.social.shashki64.shared.dto.GameDto;
import net.rushashki.social.shashki64.shared.dto.GameMessageDto;
import net.rushashki.social.shashki64.shared.locale.ShashkiConstants;
import net.rushashki.social.shashki64.shared.model.*;
import net.rushashki.social.shashki64.shared.websocket.message.MessageFactory;
import net.rushashki.social.shashki64.shashki.Board;

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
  private final ShashkiConfiguration shashkiConfiguration;
  private GameRpcServiceAsync gameService;
  private WebSocket webSocket;
  private EventBus eventBus;
  private Shashist player;
  private ShashkiConstants constants;
  private ClientFactory clientFactory;
  private ConfirmPlayDialogBox confirmPlayDialogBox;
  private HandlerRegistration playMoveHR;
  private HandlerRegistration updatePlayerListHR;
  private HandlerRegistration gameMessageHR;
  private HandlerRegistration connectToPlayHR;

  public GameWebsocket(ClientFactory clientFactory) {
    ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;

    this.clientFactory = clientFactory;
    this.player = clientFactory.getPlayer();
    this.eventBus = shashkiGinjector.getEventBus();
    this.constants = shashkiGinjector.getShashkiConstants();
    this.gameService = shashkiGinjector.getGameService();
    this.profileService = shashkiGinjector.getProfileService();
    this.shashkiConfiguration = shashkiGinjector.getShashkiConfiguration();

    handlers(clientFactory);
  }

  private void handlers(final ClientFactory clientFactory) {
    connectToPlayHR = eventBus.addHandler(ConnectToPlayEvent.TYPE, new ConnectToPlayEventHandler() {
      @Override
      public void onConnectToPlay(ConnectToPlayEvent event) {
        webSocket = new WebSocket(GameWebsocket.this);
        webSocket.connect(shashkiConfiguration.playerWebsocketUrl());
      }
    });

    gameMessageHR = eventBus.addHandler(GameMessageEvent.TYPE, new GameMessageEventHandler() {
      @Override
      public void onPlayerMessage(GameMessageEvent event) {
        GameMessage gameMessage = event.getGameMessage();

        sendGameMessage(gameMessage);
      }
    });

    playMoveHR = eventBus.addHandler(PlayMoveEvent.TYPE, new PlayMoveEventHandler() {
      @Override
      public void onPlayMove(PlayMoveEvent event) {
        GameMessage message = createSendGameMessage(clientFactory);
        message.setMessageType(Message.MessageType.PLAY_MOVE);
        message.setStartMove(event.getStartMove());
        message.setEndMove(event.getEndMove());
        message.setCaptured(event.getCaptured());
        message.setGame(clientFactory.getGame());

        sendGameMessage(message);
      }
    });

    eventBus.addHandler(WebsocketReconnectEvent.TYPE, new WebsocketReconnectEventHandler() {
      @Override
      public void onWebsocketReconnect(WebsocketReconnectEvent event) {
        removeHandlers();
        webSocket.close();
        webSocket.connect(shashkiConfiguration.playerWebsocketUrl());
      }
    });

    updatePlayerListHR = eventBus.addHandler(UpdatePlayerListEvent.TYPE, new UpdatePlayerListEventHandler() {
      @Override
      public void onUpdatePlayerList(UpdatePlayerListEvent event) {
        GameMessage message = createSendGameMessage(clientFactory);
        message.setMessageType(Message.MessageType.USER_LIST_UPDATE);
        sendGameMessage(message);
      }
    });

    eventBus.addHandler(RemovePlayMoveOpponentHandlerEvent.TYPE, new RemoveWebsocketHandlersEventHandler() {
      @Override
      public void onRemovePlayMoveOpponentHandler(RemovePlayMoveOpponentHandlerEvent event) {
        removeHandlers();
      }
    });
  }

  private void removeHandlers() {
//    playMoveHR.removeHandler();
//    updatePlayerListHR.removeHandler();
//    connectToPlayHR.removeHandler();
//    gameMessageHR.removeHandler();
  }

  private GameMessage createSendGameMessage(ClientFactory clientFactory) {
    GameMessage message = GWT.create(GameMessageDto.class);
    message.setSender(clientFactory.getPlayer());
    message.setReceiver(clientFactory.getOpponent());
    return message;
  }

  private void sendGameMessage(GameMessage gameMessage) {
    MessageFactory chatFactory = GWT.create(MessageFactory.class);
    AutoBean<GameMessage> bean = chatFactory.create(GameMessage.class, gameMessage);
    String message = AutoBeanCodex.encode(bean).getPayload();
    webSocket.send(message);
  }

  private void handleUpdatePlayerList(List<Shashist> playerList) {
    clientFactory.setPlayerList(playerList);
    eventBus.fireEvent(new GetPlayerListEvent(playerList));
  }

  private void handlePlayInvite(final GameMessage gameMessage) {
    if (confirmPlayDialogBox != null && confirmPlayDialogBox.isShowing()) {
      GameMessage message = createSendGameMessage(gameMessage);
      message.setMessageType(Message.MessageType.PLAY_ALREADY_PLAYING);
      sendGameMessage(message);
      return;
    }
    confirmPlayDialogBox = new ConfirmPlayDialogBox() {
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
                GameMessage message = createSendGameMessage(gameMessage);
                message.setMessageType(Message.MessageType.PLAY_START);

                message.setData(Boolean.TRUE.toString());
                message.setGame(game);

                sendGameMessage(message);

                clientFactory.setGame(game);
                eventBus.fireEvent(new StartPlayEvent(isWhite()));
              }
            });
          }
        });

      }

      @Override
      public void canceled() {
        GameMessage message = createSendGameMessage(gameMessage);
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
    eventBus.fireEvent(new ConnectedToPlayEvent());
  }

  @Override
  public void onDisconnect() {
    clientFactory.setConnected(false);
    eventBus.fireEvent(new DisconnectFromPlayEvent());
  }

  @Override
  public void onMessage(String message) {
    GWT.log(message);
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
      case PLAY_ALREADY_PLAYING:
        handlePlayAlreadyPlaying(gameMessage);
        break;
      case PLAY_MOVE:
        handlePlayMove(gameMessage);
        break;
      case PLAY_SURRENDER:
        handlePlaySurrender(gameMessage);
        break;
      case PLAY_PROPOSE_DRAW:
        handlePlayProposeDraw(gameMessage);
        break;
      case PLAY_ACCEPT_DRAW:
        handlePlayAcceptDraw(gameMessage);
        break;
      case PLAY_CANCEL_MOVE:
        handlePlayCancelMove(gameMessage);
        break;
      case PLAY_CANCEL_MOVE_RESPONSE:
        handlePlayCancelMoveResponse(gameMessage);
        break;
      case CHAT_PRIVATE_MESSAGE:
        handleChatPrivateMessage(gameMessage);
        break;
    }
  }

  private void handlePlayAlreadyPlaying(GameMessage gameMessage) {
    eventBus.fireEvent(new HideInviteDialogBoxEvent());
    new DialogBox(constants.info(), constants.playAlreadyPlaying(gameMessage.getSender().getPublicName()));
  }

  private void handlePlayCancelMoveResponse(GameMessage gameMessage) {
    boolean isAcceptedCancelMove = Boolean.valueOf(gameMessage.getData());
    if (isAcceptedCancelMove) {
      eventBus.fireEvent(new PlayMoveEvent(gameMessage.getStartMove(), gameMessage.getEndMove(),
          gameMessage.getCaptured()));
    } else {
      new DialogBox(constants.info(), constants.playerRejectedMoveCancel(gameMessage.getSender().getPublicName()));
    }
  }

  private void handlePlayCancelMove(final GameMessage gameMessage) {
    new ConfirmeDialogBox(constants.playerProposesCancelMove(gameMessage.getSender().getPublicName())) {
      @Override
      public void procConfirm() {
        GameMessage returnGameMessage = createSendGameMessage(gameMessage);
        returnGameMessage.setMessageType(Message.MessageType.PLAY_CANCEL_MOVE_RESPONSE);
        returnGameMessage.setStartMove(gameMessage.getStartMove());
        returnGameMessage.setEndMove(gameMessage.getEndMove());
        returnGameMessage.setCaptured(gameMessage.getCaptured());
        if (isConfirmed()) {
          returnGameMessage.setData(Boolean.TRUE.toString());
          eventBus.fireEvent(new PlayMoveEvent(gameMessage.getStartMove(), gameMessage.getEndMove(),
              gameMessage.getCaptured()));
        } else {
          returnGameMessage.setData(Boolean.FALSE.toString());
        }
        sendGameMessage(returnGameMessage);
      }
    };
  }

  private void handlePlayAcceptDraw(GameMessage gameMessage) {
    if (Boolean.valueOf(gameMessage.getData())) {
      Game game = clientFactory.getGame();
      game.setPlayEndDate(new Date());
      game.setPlayEndStatus(GameEnds.DRAW);
      gameService.saveGame(game, new AsyncCallback<Void>() {
        @Override
        public void onFailure(Throwable throwable) {
          new DialogBox(constants.error(), constants.errorWhileSavingGame());
        }

        @Override
        public void onSuccess(Void aVoid) {
          eventBus.fireEvent(new ClearPlayComponentEvent());
        }
      });
    } else {
      String senderName = gameMessage.getSender().getPublicName();
      new DialogBox(constants.info(), constants.playerRejectedDraw(senderName));
    }
  }

  private void handlePlayProposeDraw(final GameMessage gameMessage) {
    String senderName = gameMessage.getSender().getPublicName();
    new ConfirmeDialogBox(constants.playerProposesDraw(senderName)) {
      @Override
      public void procConfirm() {
        GameMessage message = createSendGameMessage(gameMessage);
        message.setMessageType(GameMessage.MessageType.PLAY_ACCEPT_DRAW);

        if (isConfirmed()) {
          message.setData(Boolean.TRUE.toString());
        } else {
          message.setData(Boolean.FALSE.toString());
        }

        sendGameMessage(message);

        if (isConfirmed()) {
          eventBus.fireEvent(new ClearPlayComponentEvent());
        }
      }
    };
  }

  private GameMessage createSendGameMessage(GameMessage gameMessage) {
    GameMessage message = GWT.create(GameMessageDto.class);
    message.setSender(gameMessage.getReceiver());
    message.setReceiver(gameMessage.getSender());
    return message;
  }

  private void handlePlaySurrender(GameMessage gameMessage) {
    Game game = clientFactory.getGame();
    GWT.log(game.toString());
    game.setPlayEndDate(new Date());
    game.setPlayEndStatus(clientFactory.isPlayerHasWhiteColor() ? GameEnds.SURRENDER_WHITE
        : GameEnds.SURRENDER_BLACK);
    gameService.saveGame(game, new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable throwable) {
        new DialogBox(constants.error(), constants.error()).show();
      }

      @Override
      public void onSuccess(Void aVoid) {
        new DialogBox(constants.info(), constants.opponentSurrendered());
        eventBus.fireEvent(new ClearPlayComponentEvent());
      }
    });
  }

  private void handlePlayMove(GameMessage gameMessage) {
    if (gameMessage.getCaptured().contains(Board.CANCEL_MOVE)) {
      return;
    }
    eventBus.fireEvent(new PlayMoveOpponentEvent(gameMessage.getStartMove(), gameMessage.getEndMove(),
        gameMessage.getCaptured()));
  }

  private void handlePlayStart(final GameMessage gameMessage) {
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
        eventBus.fireEvent(new StartPlayEvent(white));
      }
    });
  }

  private void handleChatPrivateMessage(GameMessage gameMessage) {
    eventBus.fireEvent(new ChatMessageEvent(gameMessage.getMessage()));
  }

  private void handlePlayRejectInvite(GameMessage gameMessage) {
    clientFactory.setOpponent(null);
    new DialogBox(constants.info(),
        constants.playerRejectedPlayRequest(gameMessage.getSender().getPublicName()))
        .show();
    eventBus.fireEvent(new RejectPlayEvent());
  }
}
