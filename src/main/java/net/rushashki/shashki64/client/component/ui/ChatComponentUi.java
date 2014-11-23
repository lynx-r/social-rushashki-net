package net.rushashki.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.shashki64.client.component.ChatComponent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 17:10
 */
public class ChatComponentUi extends Composite implements ChatComponent {
  interface ChatComponentImplUiBinder extends UiBinder<HTMLPanel, ChatComponentUi> {
  }

  private static ChatComponentImplUiBinder ourUiBinder = GWT.create(ChatComponentImplUiBinder.class);

  public ChatComponentUi() {
    initWidget(ourUiBinder.createAndBindUi(this));
  }
}