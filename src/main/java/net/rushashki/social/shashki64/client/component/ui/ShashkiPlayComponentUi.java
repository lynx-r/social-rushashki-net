package net.rushashki.social.shashki64.client.component.ui;

import com.ait.lienzo.client.core.shape.Layer;
import com.ait.lienzo.client.core.shape.Rectangle;
import com.ait.lienzo.client.core.shape.Text;
import com.ait.lienzo.client.widget.LienzoPanel;
import com.ait.lienzo.shared.core.types.IColor;
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
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.view.client.SingleSelectionModel;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.client.component.widget.NotationPanel;
import net.rushashki.social.shashki64.client.component.widget.dialog.InviteDialogBox;
import net.rushashki.social.shashki64.client.event.*;
import net.rushashki.social.shashki64.shared.model.Shashist;
import net.rushashki.social.shashki64.shared.resources.Resources;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessage;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessageImpl;
import net.rushashki.social.shashki64.shashki.Board;
import net.rushashki.social.shashki64.shashki.BoardBackgroundLayer;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;

import java.util.ArrayList;
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
  Button connectPlayButton;
  @UiField
  Label offlineHintLabel;
  @UiField
  HTMLPanel playerListColumn;
  @UiField
  ScrollPanel playerPanel;

  private Board board;
  private LienzoPanel lienzoPanel;
  private Shashist player;
  private CellList<Shashist> playersCellList;
  private SingleSelectionModel<Shashist> playerSelectionModel;
  private NotationPanel notationPanel;
  private InviteDialogBox inviteDialogBox;

  public ShashkiPlayComponentUi(ClientFactory clientFactory) {
    privateChat = new ChatPrivateComponentUi(clientFactory);
    initWidget(ourUiBinder.createAndBindUi(this));

    this.player = clientFactory.getPlayer();

    initEmptyDeskPanel();
    initNotationPanel();
    initCellList();

    if (clientFactory.getPlayerList() != null) {
      setPlayerList(clientFactory.getPlayerList());
    }

    connectPlayButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        switch (connectPlayButton.getIcon()) {
          case REFRESH:
            connectPlayButton.setType(ButtonType.DEFAULT);
            connectPlayButton.setIcon(IconType.PLAY);
            eventBus.fireEvent(new OnConnectToPlayEvent());
            break;
          case PLAY:
            clientFactory.setOpponent(playerSelectionModel.getSelectedObject());

            inviteDialogBox = new InviteDialogBox() {
              @Override
              public void submitted(boolean white) {
                PlayerMessage playerMessage = GWT.create(PlayerMessageImpl.class);
                playerMessage.setSender(clientFactory.getPlayer());
                playerMessage.setReceiver(clientFactory.getOpponent());
                playerMessage.setType(PlayerMessage.MessageType.PLAY_INVITE);

                playerMessage.setMessage(constants.inviteMessage(clientFactory.getPlayer().getPublicName(),
                    String.valueOf(white ? constants.black() : constants.white())));
                playerMessage.setData(String.valueOf(!white));

                eventBus.fireEvent(new OnPlayerMessageEvent(playerMessage));
              }
            };
            inviteDialogBox.show(constants.inviteToPlay(clientFactory.getOpponent().getPublicName(),
                constants.draughts()));
            break;
        }
      }
    });

    // TODO: Not Compile
    eventBus.addHandler(OnGetPlayerListEvent.TYPE, new OnGetPlayerListEventHandler() {
      @Override
      public void onOnGetPlayerList(OnGetPlayerListEvent event) {
        setPlayerList(event.getPlayerList());
      }
    });

    eventBus.addHandler(OnConnectedToPlayEvent.TYPE, new OnConnectedToPlayEventHandler() {
      @Override
      public void onOnConnectedToPlay(OnConnectedToPlayEvent event) {
        connectPlayButton.setText(constants.play());
      }
    });

    eventBus.addHandler(OnDisconnectFromPlayEvent.TYPE, new OnDisconnectFromPlayEventHandler() {
      @Override
      public void onOnDisconnectFromPlay(OnDisconnectFromPlayEvent event) {
        connectPlayButton.setActive(true);
        connectPlayButton.setBlock(true);
        connectPlayButton.addStyleName("btn-danger");
        connectPlayButton.setIcon(IconType.REFRESH);
        connectPlayButton.setText(constants.reconnect());

        playersCellList.setRowData(new ArrayList<>());
      }
    });

    eventBus.addHandler(OnStartPlayEvent.TYPE, new OnStartPlayEventHandler() {
      @Override
      public void onOnStartPlay(OnStartPlayEvent event) {
        if (inviteDialogBox != null) {
          inviteDialogBox.hide();
        }
        initDeskPanel(true);
//        lienzoPanel.add(board);
      }
    });

    eventBus.addHandler(OnRejectPlayEvent.TYPE, new OnRejectPlayEventHandler() {
      @Override
      public void onOnRejectPlay(OnRejectPlayEvent event) {
        inviteDialogBox.hide();
      }
    });
  }

  private void setPlayerList(List<Shashist> playerList) {
    playersCellList.setRowData(playerList);
  }

  private void initEmptyDeskPanel() {
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
    String[] descriptions = constants.playStartDescription().split("\n");
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

  private void initDeskPanel(boolean white) {
    int lienzoSide = lienzoPanel.getHeight() - 20;
    BoardBackgroundLayer boardBackgroundLayer = new BoardBackgroundLayer(
        lienzoSide, lienzoSide - 30,
        8, 8);
    boardBackgroundLayer.drawCoordinates(white);
    lienzoPanel.setBackgroundLayer(boardBackgroundLayer);
  }

  private void initLienzoPanel() {
//    BoardBackgroundLayer boardBackgroundLayer = new BoardBackgroundLayer(
//        lienzoSide, lienzoSide - 30,
//        8, 8);
//    lienzoPanel.setBackgroundLayer(boardBackgroundLayer);
//    shashki.add(lienzoPanel);
//
//    board = new Board(boardBackgroundLayer, 8, 8, true);
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