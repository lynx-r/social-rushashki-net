package net.rushashki.social.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.client.rpc.GameRpcService;
import net.rushashki.social.shashki64.shared.dto.PlayDto;
import net.rushashki.social.shashki64.shared.model.Game;
import org.gwtbootstrap3.client.ui.Column;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 25.11.14
 * Time: 9:18
 */
public class ShashkiListComponentImpl extends BasicComponent {

  private static final int LAST_GAMES = 5;

  interface Binder extends UiBinder<HTMLPanel, ShashkiListComponentImpl> {
  }

  private static Binder ourUiBinder = GWT.create(Binder.class);

  @UiField
  HTMLPanel shashkiList;
  @UiField
  Column newGameColumn;
  @UiField
  HTMLPanel newGamePanel;

  public ShashkiListComponentImpl() {
    initWidget(ourUiBinder.createAndBindUi(this));

    final boolean authorized = clientFactory.getPlayer() != null;
    newGameColumn.setVisible(authorized);

    List<ShashkiViewComponentImpl> plays = new ArrayList<>();

    GameRpcService.App.getInstance().findGames(0, LAST_GAMES, new AsyncCallback<List<Game>>() {
      @Override
      public void onFailure(Throwable throwable) {
        GWT.log(throwable.getLocalizedMessage(), throwable);
      }

      @Override
      public void onSuccess(List<Game> games) {
        for (Game game : games) {
          PlayDto playDto = new PlayDto(game);
          ShashkiViewComponentImpl shashkiViewComponentImpl = new ShashkiViewComponentImpl(playDto);
          plays.add(shashkiViewComponentImpl);
          playDto.setDeskHtml(shashkiViewComponentImpl::toString);
        }

        for (ShashkiViewComponentImpl play : plays) {
          shashkiList.add(play);
        }
      }
    });
  }

  @UiHandler("newGameButton")
  public void onNewGameHandler(ClickEvent event) {
    ShashkiPlayComponentImpl shashkiPlayComponent = new ShashkiPlayComponentImpl(clientFactory);
    newGamePanel.clear();
    newGamePanel.add(shashkiPlayComponent);
  }
}