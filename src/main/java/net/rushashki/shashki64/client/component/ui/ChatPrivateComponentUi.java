package net.rushashki.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 13:32
 */
public class ChatPrivateComponentUi extends BasicComponent {
  interface ChatPrivateComponentUiUiBinder extends UiBinder<HTMLPanel, ChatPrivateComponentUi> {
  }

  private static ChatPrivateComponentUiUiBinder ourUiBinder = GWT.create(ChatPrivateComponentUiUiBinder.class);

  public ChatPrivateComponentUi() {
    initWidget(ourUiBinder.createAndBindUi(this));
  }
}