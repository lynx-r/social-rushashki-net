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
import net.rushashki.social.shashki64.client.component.widget.dialog.DialogBox;
import net.rushashki.social.shashki64.client.component.widget.dialog.InviteDialogBox;
import net.rushashki.social.shashki64.client.event.*;
import net.rushashki.social.shashki64.client.rpc.GameRpcServiceAsync;
import net.rushashki.social.shashki64.shared.dto.GameMessageDto;
import net.rushashki.social.shashki64.shared.model.Game;
import net.rushashki.social.shashki64.shared.model.GameMessage;
import net.rushashki.social.shashki64.shared.model.GameProxy;
import net.rushashki.social.shashki64.shared.model.Shashist;
import net.rushashki.social.shashki64.shared.resources.Resources;
import net.rushashki.social.shashki64.shashki.Board;
import net.rushashki.social.shashki64.shashki.BoardBackgroundLayer;
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
public class ShashkiPlayComponentUi extends BasicComponent {
  private static ShashkiPlayComponentUiUiBinder ourUiBinder = GWT.create(ShashkiPlayComponentUiUiBinder.class);

  @UiField
  HTMLPanel shashki;
  @UiField
  HTMLPanel shashkiColumn;
  @UiField
  HTMLPanel privateChatColumn;
  @UiField(provided = true)
  ChatPrivateComponentUi privateChat;
  @UiField
  HTMLPanel notationColumn;
  @UiField
  HTMLPanel notationList;
  @UiField
  Button connectToPlayButton;
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

  private Board board;
  private LienzoPanel lienzoPanel;
  private Shashist player;
  private CellList<Shashist> playersCellList;
  private SingleSelectionModel<Shashist> playerSelectionModel;
  private NotationPanel notationPanel;
  private InviteDialogBox inviteDialogBox;
  private int CHECKERS_ON_DESK_INIT = 12;
  private GameRpcServiceAsync gameService;

  public ShashkiPlayComponentUi(ClientFactory clientFactory) {
    privateChat = new ChatPrivateComponentUi(clientFactory);
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
              new DialogBox(constants.info(), constants.selectPlayer()).show();
              return;
            }
            if (playerSelectionModel.getSelectedObject().getSystemId().equals(clientFactory.getPlayer().getSystemId())) {
              new DialogBox(constants.info(), constants.selectAnotherPlayerItsYou()).show();
              return;
            }
            clientFactory.setOpponent(playerSelectionModel.getSelectedObject());

            inviteDialogBox = new InviteDialogBox() {
              @Override
              public void submitted(boolean white) {
                GameMessage gameMessage = GWT.create(GameMessageDto.class);
                gameMessage.setSender(clientFactory.getPlayer());
                gameMessage.setReceiver(clientFactory.getOpponent());
                gameMessage.setMessageType(GameMessage.MessageType.PLAY_INVITE);

                gameMessage.setMessage(constants.inviteMessage(clientFactory.getPlayer().getPublicName(),
                    String.valueOf(white ? constants.black() : constants.white())));
                gameMessage.setData(String.valueOf(!white));

                eventBus.fireEvent(new GameMessageEvent(gameMessage));
                hidePlayButtonShowPlayingButtons();
              }
            };
            inviteDialogBox.show(constants.inviteToPlay(clientFactory.getOpponent().getPublicName(),
                constants.draughts()));
        }
      }
    });

    // TODO: Not Compile
    eventBus.addHandler(GetPlayerListEvent.TYPE, new GetPlayerListEventHandler() {
      @Override
      public void onGetPlayerList(GetPlayerListEvent event) {
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
        connectToPlayButton.setActive(true);
        connectToPlayButton.setBlock(true);
        connectToPlayButton.addStyleName("btn-danger");
        connectToPlayButton.setIcon(IconType.REFRESH);
        connectToPlayButton.setText(constants.reconnect());

        playersCellList.setRowData(new ArrayList<>());
        turnLabel.setHTML(constants.youDisconnected());
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
        updateTurn(clientFactory.getGame().getPlayerWhite().getSystemId().equals(clientFactory.getPlayer().getSystemId()));
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
        setBeatenMine(CHECKERS_ON_DESK_INIT - board.getMineDraughts().size());
        setBeatenOpponent(CHECKERS_ON_DESK_INIT - board.getOpponentDraughts().size());
        Game endGame = clientFactory.getGame();
        if (0 == board.getMineDraughts().size()) {
          new DialogBox(constants.info(), constants.youLose()).show();
          if (board.isWhite()) {
            endGame.setPlayEndStatus(Game.GameEnds.BLACK_WON);
          } else {
            endGame.setPlayEndStatus(GameProxy.GameEnds.WHITE_WON);
          }
        }
        if (0 == board.getOpponentDraughts().size()) {
          new DialogBox(constants.info(), constants.youWon());
          if (board.isWhite()) {
            endGame.setPlayEndStatus(Game.GameEnds.WHITE_WON);
          } else {
            endGame.setPlayEndStatus(GameProxy.GameEnds.BLACK_WON);
          }
        }
        gameService.getGame(endGame.getId(), new AsyncCallback<Game>() {
          @Override
          public void onFailure(Throwable throwable) {

          }

          @Override
          public void onSuccess(Game game) {
            if (game.getPlayEndStatus() == null) {
              if (null != endGame.getPlayEndStatus()) {
                endGame.setPartyNotation(NotationPanel.getNotation());
                endGame.setPlayEndDate(new Date());
                gameService.saveGame(endGame, new AsyncCallback<Void>() {
                  @Override
                  public void onFailure(Throwable caught) {
                    new DialogBox(constants.error(), caught.getMessage()).show();
                    caught.printStackTrace();
                  }

                  @Override
                  public void onSuccess(Void result) {
                  }
                });
              }
            }
            clearPlayComponent(clientFactory);
          }
        });
      }
    });

    eventBus.addHandler(ClearPlayComponentEvent.TYPE, new ClearPlayComponentEventHandler() {
      @Override
      public void onClearPlayComponent(ClearPlayComponentEvent event) {
        clearPlayComponent(clientFactory);
      }
    });
  }

  private void hidePlayButtonShowPlayingButtons() {
    connectToPlayButton.setVisible(false);
  }

  private void clearPlayComponent(ClientFactory clientFactory) {
    eventBus.fireEvent(new ClearNotationEvent());

    clientFactory.setOpponent(null);
    clientFactory.setGame(null);

    board.clearDesk();
    shashki.remove(lienzoPanel);
    initEmptyDeskPanel(constants.playRestartDescription());

    turnLabel.setHTML(constants.playDidNotStart());
  }

  public void setBeatenMine(int count) {
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
      int side = Window.getClientWidth() - shashkiColumn.getOffsetWidth() - notationColumn.getOffsetWidth()
          - playerListColumn.getOffsetWidth() - 81;
      privateChatColumn.setWidth(side + "px");
      String notationHeight = lienzoPanel.getHeight() - 50 + "px";
      notationPanel.setHeight(notationHeight);
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

  interface ShashkiPlayComponentUiUiBinder extends UiBinder<HTMLPanel, ShashkiPlayComponentUi> {
  }
}