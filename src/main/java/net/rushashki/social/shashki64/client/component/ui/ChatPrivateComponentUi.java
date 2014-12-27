package net.rushashki.social.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.client.component.widget.dialog.DialogBox;
import net.rushashki.social.shashki64.client.event.OnChatMessageEvent;
import net.rushashki.social.shashki64.client.event.OnChatMessageEventHandler;
import net.rushashki.social.shashki64.client.event.OnPlayerMessageEvent;
import net.rushashki.social.shashki64.client.util.Util;
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

    eventBus.addHandler(OnChatMessageEvent.TYPE, new OnChatMessageEventHandler() {
      @Override
      public void onOnChatMessage(OnChatMessageEvent event) {
        chatPanel.add(new Label(event.getMessage()));
      }
    });

    messageInputTextBox.addKeyPressHandler(new KeyPressHandler() {
      @Override
      public void onKeyPress(KeyPressEvent keyPressEvent) {
        if (keyPressEvent.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER && !messageInputTextBox.getText().isEmpty()) {
          if (clientFactory.getOpponent() == null) {
            DialogBox dialogBox = new DialogBox(constants.info(), constants.atFirstStartPlay());
            dialogBox.show();
            return;
          }
          PlayerMessage playerMessage = GWT.create(PlayerMessageImpl.class);
          playerMessage.setSender(clientFactory.getPlayer());
          playerMessage.setReceiver(clientFactory.getOpponent());
          playerMessage.setType(PlayerMessage.MessageType.CHAT_PRIVATE_MESSAGE);

          String message = clientFactory.getPlayer().getPublicName()
              + Util.MESSAGE_SEPARATOR + messageInputTextBox.getText();

          playerMessage.setMessage(message);

          eventBus.fireEvent(new OnChatMessageEvent(message));
          eventBus.fireEvent(new OnPlayerMessageEvent(playerMessage));

          messageInputTextBox.setText("");
        }
      }
    });
  }

  interface ChatPrivateComponentUiUiBinder extends UiBinder<HTMLPanel, ChatPrivateComponentUi> {
  }

}