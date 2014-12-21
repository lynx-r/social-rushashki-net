package net.rushashki.social.shashki64.client.component.ui;

import com.ait.lienzo.client.widget.LienzoPanel;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import net.rushashki.social.shashki64.client.component.widget.NotationPanel;
import net.rushashki.social.shashki64.client.event.OnClientFactoryEvent;
import net.rushashki.social.shashki64.client.event.OnClientFactoryEventHandler;
import net.rushashki.social.shashki64.client.event.OnGetPlayerListEvent;
import net.rushashki.social.shashki64.client.event.OnGetPlayerListEventHandler;
import net.rushashki.social.shashki64.shared.model.Shashist;
import net.rushashki.social.shashki64.shared.resources.Resources;
import net.rushashki.social.shashki64.shashki.Board;
import net.rushashki.social.shashki64.shashki.BoardBackgroundLayer;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.constants.IconType;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 13:12
 */
public class ShashkiPlayComponentUi extends BasicComponent {
    private static ShashkiPlayComponentUiUiBinder ourUiBinder = GWT.create(ShashkiPlayComponentUiUiBinder.class);
    private Board board;

    @UiField
    HTMLPanel shashki;
    @UiField
    HTMLPanel shashkiColumn;
    @UiField
    HTMLPanel privateChatColumn;
    @UiField
    HTMLPanel notationColumn;
    @UiField
    HTMLPanel notationList;
    @UiField
    Button playButton;
    @UiField
    Label offlineHintLabel;
    @UiField
    HTMLPanel playerListColumn;
    @UiField
    ScrollPanel playerPanel;

    private LienzoPanel lienzoPanel;
    private Shashist player;
    private CellList<Shashist> playersCellList;
    private NotationPanel notationPanel;

    public ShashkiPlayComponentUi(Shashist player, List<Shashist> playerList) {
        initWidget(ourUiBinder.createAndBindUi(this));

        this.player = player;

        initLienzoPanel();
        initNotationPanel();
        initCellList();

        if (playerList != null) {
            setPlayerList(playerList);
        }

        // TODO: Not Compile
        eventBus.addHandler(OnGetPlayerListEvent.TYPE, new OnGetPlayerListEventHandler() {
            @Override
            public void onOnGetPlayerList(OnGetPlayerListEvent event) {
                setPlayerList(event.getPlayerList());
            }
        });

        eventBus.addHandler(OnClientFactoryEvent.TYPE, new OnClientFactoryEventHandler() {
            @Override
            public void onOnClientFactory(OnClientFactoryEvent event) {
                Shashist shashist = event.getClientFactory().getPlayer();
                if (shashist.getSystemId().equals(player.getSystemId()) && !shashist.isOnline()) {
                    playButton.setActive(true);
                    playButton.setBlock(true);
                    playButton.addStyleName("btn-danger");
                    playButton.setIcon(IconType.BAN);
                    offlineHintLabel.setText(constants.updatePageToPlay());
                }
            }
        });
    }

    private void setPlayerList(List<Shashist> playerList) {
        playersCellList.setRowData(playerList);
    }

    private void initLienzoPanel() {
        int shashkiSide = Window.getClientHeight() - RootPanel.get("navigation").getOffsetHeight() -
                RootPanel.get("footer").getOffsetHeight();
        shashkiColumn.setWidth(shashkiSide + "px");

        lienzoPanel = new LienzoPanel(shashkiSide, shashkiSide);
        int lienzoSide = lienzoPanel.getHeight() - 20;
        BoardBackgroundLayer boardBackgroundLayer = new BoardBackgroundLayer(
                lienzoSide, lienzoSide - 30,
                8, 8);
        lienzoPanel.setBackgroundLayer(boardBackgroundLayer);
        shashki.add(lienzoPanel);

        board = new Board(boardBackgroundLayer, 8, 8, true);
        lienzoPanel.add(board);
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
        playerPanel.add(playersCellList);
    }

    interface ShashkiPlayComponentUiUiBinder extends UiBinder<HTMLPanel, ShashkiPlayComponentUi> {
    }
}