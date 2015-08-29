package net.rushashki.social.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.social.shashki64.client.component.ChatComponent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 17:10
 */
public class ChatComponentImpl extends Composite implements ChatComponent {
  interface Binder extends UiBinder<HTMLPanel, ChatComponentImpl> {
  }

  private static Binder ourUiBinder = GWT.create(Binder.class);

  public ChatComponentImpl() {
    initWidget(ourUiBinder.createAndBindUi(this));
  }
}