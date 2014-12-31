package net.rushashki.social.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.client.component.widget.dialog.DialogBox;
import net.rushashki.social.shashki64.client.event.*;
import net.rushashki.social.shashki64.client.util.Util;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessage;
import net.rushashki.social.shashki64.shared.websocket.message.PlayerMessageImpl;
import org.gwtbootstrap3.client.ui.TextBox;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 13:32
 */
public class ChatPrivateComponentUi extends BasicComponent {

  private static final String PREV_MESSAGE = "prev_message";
  private static ChatPrivateComponentUiUiBinder ourUiBinder = GWT.create(ChatPrivateComponentUiUiBinder.class);

  @UiField
  HTMLPanel chatPanel;
  @UiField
  TextBox messageInputTextBox;

  public ChatPrivateComponentUi(final ClientFactory clientFactory) {
    initWidget(ourUiBinder.createAndBindUi(this));

    eventBus.addHandler(OnStartPlayEvent.TYPE, new OnStartPlayEventHandler() {
      @Override
      public void onOnStartPlay(OnStartPlayEvent event) {
        playerMessageService.getLastPlayerMessages(200, clientFactory.getPlayer().getId(),
            clientFactory.getOpponent().getId(), new AsyncCallback<List<PlayerMessage>>() {
          @Override
          public void onFailure(Throwable throwable) {
            throwable.printStackTrace();
            Window.alert(throwable.getLocalizedMessage());
          }

          @Override
          public void onSuccess(List<PlayerMessage> playerMessages) {
            chatPanel.clear();
            for (PlayerMessage playerMessage : playerMessages) {
              chatPanelAddMessage(playerMessage.getMessage(), PREV_MESSAGE);
            }
          }
        });
      }
    });

    eventBus.addHandler(OnChatMessageEvent.TYPE, new OnChatMessageEventHandler() {
      @Override
      public void onOnChatMessage(OnChatMessageEvent event) {
        String message = event.getMessage();
        String publicName = clientFactory.getPlayer().getPublicName();
        chatPanelAddMessage(message, publicName);
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

  private void chatPanelAddMessage(String message, String publicName) {
    HTML messageStyled = new HTML(message);
    if (publicName.equals(PREV_MESSAGE)) {
      messageStyled.setStyleName("chat-prev-messages");
    } else {
      if (message.substring(0, message.indexOf(Util.MESSAGE_SEPARATOR)).equals(publicName)) {
        messageStyled.setStyleName("chat-my-message");
      } else if (message.substring(message.indexOf(Util.MESSAGE_SEPARATOR)).contains(publicName)) {
        messageStyled.setStyleName("chat-refer-me");
      }
    }
    chatPanel.add(messageStyled);
  }

  interface ChatPrivateComponentUiUiBinder extends UiBinder<HTMLPanel, ChatPrivateComponentUi> {
  }

}