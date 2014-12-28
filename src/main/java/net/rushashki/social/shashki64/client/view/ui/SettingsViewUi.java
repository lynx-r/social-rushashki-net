package net.rushashki.social.shashki64.client.view.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.social.shashki64.client.component.widget.dialog.DialogBox;
import net.rushashki.social.shashki64.client.view.SettingsView;
import net.rushashki.social.shashki64.shared.model.Shashist;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextBox;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 06.12.14
 * Time: 11:40
 */
public class SettingsViewUi extends BasicViewUi implements SettingsView {

  private static SettingsViewUiUiBinder ourUiBinder = GWT.create(SettingsViewUiUiBinder.class);
  @UiField
  TextBox playerNameTextBox;
  @UiField
  Button submitPlayerNameButton;
  private Shashist player;

  public SettingsViewUi() {
    initWidget(ourUiBinder.createAndBindUi(this));

    submitPlayerNameButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        if (player.getPublicName().equals(playerNameTextBox.getText())) {
          return;
        }
        if (playerNameTextBox.getText().length() > 50) {
          DialogBox dialogBox = new DialogBox(constants.error(), constants.tooLongPlayerName());
          dialogBox.getElement().getStyle().setZIndex(1000);
          dialogBox.show();
        }
        player.setPlayerName(playerNameTextBox.getText());
        profileService.saveProfile(player, new AsyncCallback<Void>() {
          @Override
          public void onFailure(Throwable throwable) {
            throwable.printStackTrace();
            DialogBox dialogBox = new DialogBox(constants.error(), constants.errorWhileProfileUpdate());
            dialogBox.getElement().getStyle().setZIndex(1000);
            dialogBox.show();
          }

          @Override
          public void onSuccess(Void aVoid) {
            DialogBox dialogBox = new DialogBox(constants.info(), constants.profileUpdated());
            dialogBox.getElement().getStyle().setZIndex(1000);
            dialogBox.show();
          }
        });
      }
    });
  }

  @Override
  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  public void setPlayer(Shashist player) {
    this.player = player;
    playerNameTextBox.setText(player.getPublicName());
  }

  interface SettingsViewUiUiBinder extends UiBinder<HTMLPanel, SettingsViewUi> {
  }

}