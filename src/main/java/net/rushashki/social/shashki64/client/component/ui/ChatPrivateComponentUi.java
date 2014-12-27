package net.rushashki.social.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.client.event.OnPlayerMessageEvent;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessage;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessageImpl;
import org.gwtbootstrap3.client.ui.TextBox;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 13:32
 */
public class ChatPrivateComponentUi extends BasicComponent {

  private static ChatPrivateComponentUiUiBinder ourUiBinder = GWT.create(ChatPrivateComponentUiUiBinder.class);

  @UiField
  HTMLPanel chatPanel;
  @UiField
  TextBox messageInputTextBox;

  public ChatPrivateComponentUi(ClientFactory clientFactory) {
    initWidget(ourUiBinder.createAndBindUi(this));

    messageInputTextBox.addKeyPressHandler(keyPressEvent -> {
      if (keyPressEvent.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER && !messageInputTextBox.getText().isEmpty()) {
        PlayerMessage playerMessage = GWT.create(PlayerMessageImpl.class);
        playerMessage.setSender(clientFactory.getPlayer());
        playerMessage.setType(PlayerMessage.MessageType.PLAYER_REGISTER);

        eventBus.fireEvent(new OnPlayerMessageEvent(playerMessage));
        messageInputTextBox.setText("");
      }
    });
  }

  interface ChatPrivateComponentUiUiBinder extends UiBinder<HTMLPanel, ChatPrivateComponentUi> {
  }

}