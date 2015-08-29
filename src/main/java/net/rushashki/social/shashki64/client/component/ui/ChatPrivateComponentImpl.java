package net.rushashki.social.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.client.component.widget.dialog.DialogBox;
import net.rushashki.social.shashki64.client.event.*;
import net.rushashki.social.shashki64.client.util.Util;
import net.rushashki.social.shashki64.shared.model.GameMessage;
import net.rushashki.social.shashki64.shared.dto.GameMessageDto;
import org.gwtbootstrap3.client.ui.TextBox;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 13:32
 */
public class ChatPrivateComponentImpl extends BasicComponent {

  private static final String PREV_MESSAGE = "prev_message";
  private static Binder ourUiBinder = GWT.create(Binder.class);

  @UiField
  HTMLPanel chatPanel;
  @UiField
  TextBox messageInputTextBox;

  public ChatPrivateComponentImpl(final ClientFactory clientFactory) {
    initWidget(ourUiBinder.createAndBindUi(this));

    eventBus.addHandler(StartPlayEvent.TYPE, new StartPlayEventHandler() {
      @Override
      public void onStartPlay(StartPlayEvent event) {
        gameMessageService.getLastPlayerMessages(200, clientFactory.getPlayer().getId(),
            clientFactory.getOpponent().getId(), new AsyncCallback<List<GameMessage>>() {
          @Override
          public void onFailure(Throwable throwable) {
            throwable.printStackTrace();
          }

          @Override
          public void onSuccess(List<GameMessage> gameMessages) {
            chatPanel.clear();
            for (GameMessage gameMessage : gameMessages) {
              chatPanelAddMessage(gameMessage.getMessage(), PREV_MESSAGE);
            }
          }
        });
      }
    });

    eventBus.addHandler(ChatMessageEvent.TYPE, new ChatMessageEventHandler() {
      @Override
      public void onChatMessage(ChatMessageEvent event) {
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
          GameMessage gameMessage = GWT.create(GameMessageDto.class);
          gameMessage.setSender(clientFactory.getPlayer());
          gameMessage.setReceiver(clientFactory.getOpponent());
          gameMessage.setMessageType(GameMessage.MessageType.CHAT_PRIVATE_MESSAGE);

          String message = clientFactory.getPlayer().getPublicName()
              + Util.MESSAGE_SEPARATOR + messageInputTextBox.getText();

          gameMessage.setMessage(message);

          eventBus.fireEvent(new ChatMessageEvent(message));
          eventBus.fireEvent(new GameMessageEvent(gameMessage));

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

  interface Binder extends UiBinder<HTMLPanel, ChatPrivateComponentImpl> {
  }

}