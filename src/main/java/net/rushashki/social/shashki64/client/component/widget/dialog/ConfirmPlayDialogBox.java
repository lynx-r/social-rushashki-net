package net.rushashki.social.shashki64.client.component.widget.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import net.rushashki.social.shashki64.shared.model.Shashist;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 10.02.14
 * Time: 9:16
 */
public abstract class ConfirmPlayDialogBox extends BasicDialogBox {

  private final Label confirmLabel = new Label();
  private String senderId;
  private boolean white;
  private String senderName;

  public ConfirmPlayDialogBox() {
    setText("Игра");
    VerticalPanel panel = new VerticalPanel();
    panel.add(confirmLabel);

    ButtonGroup group = new ButtonGroup();
    Button yesButton = new Button(constants.yes());
    Button noButton = new Button(constants.no());
    group.add(noButton);
    group.add(yesButton);

    yesButton.addStyleName("btn-primary");

    panel.add(group);
    panel.setCellHorizontalAlignment(group, HasAlignment.ALIGN_RIGHT);

    add(panel);

    yesButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        hide();
        submitted();
      }
    });
    noButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        hide();
        canceled();
      }
    });

  }

  public abstract void submitted();

  public abstract void canceled();

  public boolean isWhite() {
    return white;
  }

  public String getSenderId() {
    return senderId;
  }

  public String getSenderName() {
    return senderName;
  }

  public void show(String message, Shashist sender, boolean playWithColor) {
    this.senderId = sender.getSessionId();
    this.senderName = sender.getPublicName();
    this.white = playWithColor;
    show();
    confirmLabel.setText(message);
    center();
  }
}
