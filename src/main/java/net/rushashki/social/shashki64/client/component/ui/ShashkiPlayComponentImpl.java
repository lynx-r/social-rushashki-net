package net.rushashki.social.shashki64.client.component.ui;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.SingleSelectionModel;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.client.component.widget.NotationPanel;
import net.rushashki.social.shashki64.client.component.widget.dialog.ConfirmeDialogBox;
import net.rushashki.social.shashki64.client.component.widget.dialog.DialogBox;
import net.rushashki.social.shashki64.client.component.widget.dialog.InviteDialogBox;
import net.rushashki.social.shashki64.client.event.*;
import net.rushashki.social.shashki64.client.rpc.GameRpcServiceAsync;
import net.rushashki.social.shashki64.shared.dto.GameMessageDto;
import net.rushashki.social.shashki64.shared.model.Game;
import net.rushashki.social.shashki64.shared.model.GameEnds;
import net.rushashki.social.shashki64.shared.model.GameMessage;
import net.rushashki.social.shashki64.shared.model.Shashist;
import net.rushashki.social.shashki64.shared.resources.Resources;
import net.rushashki.social.shashki64.shashki.Board;
import net.rushashki.social.shashki64.shashki.BoardBackgroundLayer;
import net.rushashki.social.shashki64.shashki.dto.MoveDto;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 13:12
 */
public class ShashkiPlayComponentImpl extends BasicComponent {
  private static Binder ourUiBinder = GWT.create(Binder.class);

  @UiField
  HTMLPanel shashki;
  @UiField
  HTMLPanel shashkiColumn;
//  @UiField
//  HTMLPanel privateChatColumn;
//  @UiField(provided = true)
//  ChatPrivateComponentImpl privateChat;
  @UiField
  HTMLPanel notationColumn;
  @UiField
  HTMLPanel notationList;
  @UiField
  Button connectToPlayButton;
  @UiField
  Button drawButton;
  @UiField
  Button surrenderButton;
  @UiField
  HTMLPanel playerListColumn;
  @UiField
  ScrollPanel playerPanel;
  @UiField
  HTML turnLabel;
  @UiField
  Label beatenOpponentDraughtsLabel;
  @UiField
  Label beatenMineDraughtsLabel;
  @UiField
  Button cancelMove;

  private Board board;
  private LienzoPanel lienzoPanel;
  private Shashist player;
  private CellList<Shashist> playersCellList;
  private SingleSelectionModel<Shashist> playerSelectionModel;
  private NotationPanel notationPanel;
  private InviteDialogBox inviteDialogBox;
  private int CHECKERS_ON_DESK_INIT = 12;
  private GameRpcServiceAsync gameService;

  public ShashkiPlayComponentImpl(ClientFactory clientFactory) {
//    privateChat = new ChatPrivateComponentImpl(clientFactory);
    initWidget(ourUiBinder.createAndBindUi(this));

    this.player = clientFactory.getPlayer();
    this.gameService = shashkiGinjector.getGameService();

    initEmptyDeskPanel(constants.playStartDescription());
    initNotationPanel();
    initCellList();

    if (clientFactory.getPlayerList() != null) {
      setPlayerList(clientFactory.getPlayerList());
    }

    if (clientFactory.isConnected()) {
      toggleInPlayButton();
    }

    buttons(clientFactory);
    handlers(clientFactory);
  }

  public void buttons(final ClientFactory clientFactory) {
    connectToPlayButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        switch (connectToPlayButton.getIcon()) {
          case REFRESH:
            if (clientFactory.isConnected()) {
              return;
            }
            eventBus.fireEvent(new ConnectToPlayEvent());
            return;
          case PLAY:
            if (playerSelectionModel.getSelectedObject() == null) {
              new DialogBox(constants.info(), constants.selectPlayer());
              return;
            }
            if (playerSelectionModel.getSelectedObject().getSystemId().equals(clientFactory.getPlayer().getSystemId())) {
              new DialogBox(constants.info(), constants.selectAnotherPlayerItsYou());
              return;
            }
            clientFactory.setOpponent(playerSelectionModel.getSelectedObject());

            inviteDialogBox = new InviteDialogBox() {
              @Override
              public void submitted(boolean white) {
                GameMessage gameMessage = createSendGameMessage(clientFactory);
                gameMessage.setMessageType(GameMessage.MessageType.PLAY_INVITE);

                gameMessage.setMessage(constants.inviteMessage(clientFactory.getPlayer().getPublicName(),
                        String.valueOf(white ? constants.black() : constants.white())));
                gameMessage.setData(String.valueOf(!white));

                eventBus.fireEvent(new GameMessageEvent(gameMessage));
              }
            };
            inviteDialogBox.show(constants.inviteToPlay(clientFactory.getOpponent().getPublicName(),
                    constants.draughts()));
        }
      }
    });

    drawButton.addClickHandler(clickEvent -> {
      new ConfirmeDialogBox(constants.doYouWantToProposeDraw()) {
        @Override
        public void procConfirm() {
          if (isConfirmed()) {
            GameMessage gameMessage = createSendGameMessage(clientFactory);
            gameMessage.setMessageType(GameMessage.MessageType.PLAY_PROPOSE_DRAW);
            eventBus.fireEvent(new GameMessageEvent(gameMessage));
          }
        }
      };
    });

    surrenderButton.addClickHandler(clickEvent -> {
      new ConfirmeDialogBox(constants.areYouSureYouWantSurrender()) {
        @Override
        public void procConfirm() {
          if (isConfirmed()) {
            GameMessage gameMessage = createSendGameMessage(clientFactory);
            gameMessage.setMessageType(GameMessage.MessageType.PLAY_SURRENDER);
            eventBus.fireEvent(new GameMessageEvent(gameMessage));
            eventBus.fireEvent(new ClearPlayComponentEvent());
          }
        }
      };
    });

    cancelMove.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        if (board == null) {
          return;
        }
        final MoveDto lastMove = board.getLastMove();
        final MoveDto lastOpponentMove = board.getLastOpponentMove();
        if (lastOpponentMove != null && lastOpponentMove.isContinueBeat()) {
          return;
        }
        if (board.isMyTurn() && !(lastMove != null && lastMove.isContinueBeat())) {
          new DialogBox(constants.info(), constants.youDontMove());
          return;
        }
        new ConfirmeDialogBox(constants.doYouWantToCancelMove()) {
          @Override
          public void procConfirm() {
            if (isConfirmed()) {
              GameMessage gameMessage = createSendGameMessage(clientFactory);
              gameMessage.setMessageType(GameMessage.MessageType.PLAY_CANCEL_MOVE);
              if (lastMove.isContinueBeat()) {
                lastMove.mirror();
              }
              lastMove.setOnCancelMove();
              gameMessage.setMove(lastMove);

              eventBus.fireEvent(new GameMessageEvent(gameMessage));
            }
          }
        };
      }
    });
  }

  public void handlers(final ClientFactory clientFactory) {
    eventBus.addHandler(GetPlayerListEvent.TYPE, new GetPlayerListEventHandler() {
      @Override
      public void onGetPlayerList(GetPlayerListEvent event) {
        if (!event.getPlayerList().contains(clientFactory.getOpponent()) && clientFactory.getGame() != null) {
          Game game = clientFactory.getGame();
          game.setPlayEndStatus(clientFactory.isPlayerHasWhiteColor() ? GameEnds.BLACK_LEFT : GameEnds.WHITE_LEFT);
          game.setPlayEndDate(new Date());
          gameService.saveGame(game, new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
              new DialogBox(constants.error(), constants.errorWhileSavingGame());
            }

            @Override
            public void onSuccess(Void aVoid) {
              new DialogBox(constants.info(), constants.opponentLeftGame()) {
                @Override
                public void submit() {
                  eventBus.fireEvent(new ClearPlayComponentEvent());
                }
              };
            }
          });
          return;
        }
        setPlayerList(event.getPlayerList());
      }
    });

    eventBus.addHandler(ConnectedToPlayEvent.TYPE, new ConnectedToPlayEventHandler() {
      @Override
      public void onConnectedToPlay(ConnectedToPlayEvent event) {
        toggleInPlayButton();
      }
    });

    eventBus.addHandler(DisconnectFromPlayEvent.TYPE, new DisconnectFromPlayEventHandler() {
      @Override
      public void onDisconnectFromPlay(DisconnectFromPlayEvent event) {
        GWT.log("Disconnected from Play");
        connectToPlayButton.setActive(true);
        connectToPlayButton.setBlock(true);
        connectToPlayButton.addStyleName("btn-danger");
        connectToPlayButton.setIcon(IconType.REFRESH);
        connectToPlayButton.setText(constants.reconnect());

        playersCellList.setRowData(new ArrayList<>());
        turnLabel.setHTML(constants.youDisconnected());

        hidePlayingButtonsAndShowPlayButton();
      }
    });

    eventBus.addHandler(StartPlayEvent.TYPE, new StartPlayEventHandler() {
      @Override
      public void onStartPlay(StartPlayEvent event) {
        if (inviteDialogBox != null) {
          inviteDialogBox.hide();
        }
        BoardBackgroundLayer backgroundLayer = initDeskPanel(event.isWhite());
        board = new Board(backgroundLayer, 8, 8, event.isWhite());
        lienzoPanel.add(board);
        updateTurn(clientFactory.getGame().getPlayerWhite().getSystemId()
            .equals(clientFactory.getPlayer().getSystemId()));
        hidePlayButtonAndShowPlayingButtons();
      }
    });

    eventBus.addHandler(RejectPlayEvent.TYPE, new RejectPlayEventHandler() {
      @Override
      public void onRejectPlay(RejectPlayEvent event) {
        inviteDialogBox.hide();
      }
    });

    eventBus.addHandler(TurnChangeEvent.TYPE, new TurnChangeEventHandler() {
      @Override
      public void onTurnChange(TurnChangeEvent event) {
        updateTurn(event.isMyTurn());
      }
    });

    eventBus.addHandler(CheckWinnerEvent.TYPE, new CheckWinnerEventHandler() {
      @Override
      public void onCheckWinner(CheckWinnerEvent event) {
        setBeatenMy(CHECKERS_ON_DESK_INIT - board.getMyDraughts().size());
        setBeatenOpponent(CHECKERS_ON_DESK_INIT - board.getOpponentDraughts().size());
        Game endGame = clientFactory.getGame();
        if (0 == board.getMyDraughts().size()) {
          new DialogBox(constants.info(), constants.youLose()).show();
          if (board.isWhite()) {
            endGame.setPlayEndStatus(GameEnds.BLACK_WON);
          } else {
            endGame.setPlayEndStatus(GameEnds.WHITE_WON);
          }
        }
        if (0 == board.getOpponentDraughts().size()) {
          new DialogBox(constants.info(), constants.youWon());
          if (board.isWhite()) {
            endGame.setPlayEndStatus(GameEnds.WHITE_WON);
          } else {
            endGame.setPlayEndStatus(GameEnds.BLACK_WON);
          }
        }
        if (endGame.getPlayEndStatus() == null) {
          return;
        }
        gameService.getGame(endGame.getId(), new AsyncCallback<Game>() {
          @Override
          public void onFailure(Throwable throwable) {
            new DialogBox(constants.error(), constants.errorWhileGettingGame());
          }

          @Override
          public void onSuccess(Game game) {
            if (game.getPlayEndStatus() == null) {
              endGame.setPartyNotation(NotationPanel.getNotation());
              endGame.setPlayEndDate(new Date());
              gameService.saveGame(endGame, new AsyncCallback<Void>() {
                @Override
                public void onFailure(Throwable caught) {
                  new DialogBox(constants.error(), constants.errorWhileSavingGame());
                }

                @Override
                public void onSuccess(Void result) {
                  eventBus.fireEvent(new ClearPlayComponentEvent());
                }
              });
            }
          }
        });
      }
    });

    eventBus.addHandler(ClearPlayComponentEvent.TYPE, new ClearPlayComponentEventHandler() {
      @Override
      public void onClearPlayComponent(ClearPlayComponentEvent event) {
        clearPlayComponent(clientFactory);
        hidePlayingButtonsAndShowPlayButton();
//        board = null;
      }
    });

    eventBus.addHandler(HideInviteDialogBoxEvent.TYPE, new HideInviteDialogBoxEventHandler() {
      @Override
      public void onHideInviteDialogBox(HideInviteDialogBoxEvent event) {
        if (inviteDialogBox != null && inviteDialogBox.isVisible()) {
          inviteDialogBox.hide();
        }
      }
    });
  }

  private GameMessage createSendGameMessage(ClientFactory clientFactory) {
    GameMessage gameMessage = GWT.create(GameMessageDto.class);
    gameMessage.setSender(clientFactory.getPlayer());
    gameMessage.setReceiver(clientFactory.getOpponent());
    gameMessage.setGame(clientFactory.getGame());
    return gameMessage;
  }

  private void hidePlayingButtonsAndShowPlayButton() {
    connectToPlayButton.setVisible(true);
    drawButton.setVisible(false);
    surrenderButton.setVisible(false);
    setBeatenMy(0);
    setBeatenOpponent(0);
  }

  private void hidePlayButtonAndShowPlayingButtons() {
    connectToPlayButton.setVisible(false);
    drawButton.setVisible(true);
    surrenderButton.setVisible(true);
    setBeatenMy(0);
    setBeatenOpponent(0);
  }

  private void clearPlayComponent(ClientFactory clientFactory) {
    eventBus.fireEvent(new ClearNotationEvent());
    eventBus.fireEvent(new UpdatePlayerListEvent());
    eventBus.fireEvent(new RemovePlayMoveOpponentHandlerEvent());

    clientFactory.setOpponent(null);
    clientFactory.setGame(null);

    lienzoPanel.removeAll();
    board.clearDesk();
    shashki.remove(lienzoPanel);
    initEmptyDeskPanel(constants.playRestartDescription());

    turnLabel.setHTML(constants.playDidNotStart());
  }

  public void setBeatenMy(int count) {
    beatenMineDraughtsLabel.setText(count + " - " + (board.isWhite() ? constants.whites()
        : constants.blacks()));
  }

  public void setBeatenOpponent(int count) {
    beatenOpponentDraughtsLabel.setText(count + " - " + (board.isWhite() ? constants.blacks()
        : constants.whites()));
  }

  private void updateTurn(boolean myTurn) {
    if (myTurn) {
      turnLabel.setHTML(constants.yourTurn());
    } else {
      turnLabel.setHTML(constants.opponentTurn());
    }
  }

  private void toggleInPlayButton() {
    connectToPlayButton.setType(ButtonType.DEFAULT);
    connectToPlayButton.setIcon(IconType.PLAY);
    connectToPlayButton.setText(constants.play());
  }

  private void setPlayerList(List<Shashist> playerList) {
    playersCellList.setRowData(playerList);
  }

  private void initEmptyDeskPanel(String playStartDescription) {
    int shashkiSide = Window.getClientHeight() - RootPanel.get("navigation").getOffsetHeight() -
        RootPanel.get("footer").getOffsetHeight();
    shashkiColumn.setWidth(shashkiSide + "px");

    lienzoPanel = new LienzoPanel(shashkiSide, shashkiSide);
    int lienzoSide = lienzoPanel.getHeight() - 20;
    Layer initDeskRect = new Layer();
    Rectangle contour = new Rectangle(lienzoSide, lienzoSide);
    contour.setX(1);
    contour.setY(1);
    initDeskRect.add(contour);
    String[] descriptions = playStartDescription.split("\n");
    int y = 0;
    for (String description : descriptions) {
      Text greeting = new Text(description, "Times New Roman", 14);
      greeting.setFillColor("blue");
      greeting.setStrokeColor("blue");
      greeting.setY(lienzoSide / 2 - 20 + y);
      greeting.setX(lienzoSide / 2 - 180);
      initDeskRect.add(greeting);
      y += 20;
    }
    lienzoPanel.setBackgroundLayer(initDeskRect);
    shashki.add(lienzoPanel);
  }

  private BoardBackgroundLayer initDeskPanel(boolean white) {
    int lienzoSide = lienzoPanel.getHeight() - 20;
    BoardBackgroundLayer boardBackgroundLayer = new BoardBackgroundLayer(
        lienzoSide, lienzoSide - 30,
        8, 8);
    boardBackgroundLayer.drawCoordinates(white);
    lienzoPanel.setBackgroundLayer(boardBackgroundLayer);
    return boardBackgroundLayer;
  }

  private void initNotationPanel() {
    notationPanel = new NotationPanel();
    notationList.add(notationPanel);

    Scheduler.get().scheduleFinally(this::alignNotationPanel);
  }

  private void alignNotationPanel() {
    if (Window.getClientWidth() > 0) {
      // 81 - отступы
//      int side = Window.getClientWidth() - shashkiColumn.getOffsetWidth() - notationColumn.getOffsetWidth()
//          - playerListColumn.getOffsetWidth() - 81;
//      privateChatColumn.setWidth(side + "px");
      String notationHeight = lienzoPanel.getHeight() - 170 + "px";
      notationPanel.setHeight(notationHeight);
      String playerListHeight = lienzoPanel.getHeight() - 105 + "px";
      playersCellList.setHeight(playerListHeight);
    }
  }

  private void initCellList() {
    playersCellList = new CellList<>(new AbstractCell<Shashist>() {
      @Override
      public void render(Context context, Shashist value, SafeHtmlBuilder sb) {
        if (value != null) {
          if (value.isLoggedIn()) {
            Image img;
            String playerPublicName = value.getPublicName();
            if (player.getSystemId().equals(value.getSystemId())) {
              sb.appendEscaped(playerPublicName);
            } else {
              if (value.isPlaying()) {
                img = new Image(Resources.INSTANCE.images().playingIconImage().getSafeUri());
                img.setTitle(playerPublicName + " играет");
              } else {
                if (value.isOnline()) {
                  img = new Image(Resources.INSTANCE.images().onlineIconImage().getSafeUri());
                  img.setTitle(playerPublicName + " в сети");
                } else {
                  img = new Image(Resources.INSTANCE.images().offlineIconImage().getSafeUri());
                  img.setTitle(playerPublicName + " не в сети");
                }
              }
              sb.appendHtmlConstant(img.getElement().getString());
              sb.appendEscaped(" " + playerPublicName);
            }
          }
        }
      }
    });
    playerSelectionModel = new SingleSelectionModel<>();
    playersCellList.setSelectionModel(playerSelectionModel);
    playerPanel.add(playersCellList);
  }

  interface Binder extends UiBinder<HTMLPanel, ShashkiPlayComponentImpl> {
  }
}