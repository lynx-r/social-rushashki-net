package net.rushashki.social.shashki64.client.component.widget.dialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.ButtonGroup;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 10/03/14
 * Time: 11:07
 */
public abstract class ConfirmDialogBox extends BasicDialogBox {

  private boolean confirmed;

  public ConfirmDialogBox(String ask) {
    setText(constants.confirm());
    setModal(true);

    VerticalPanel panel = new VerticalPanel();

    Label askLabel = new Label(ask);
    panel.add(askLabel);

    ButtonGroup askButtons = new ButtonGroup();
    Button yesButton = new Button(constants.yes());
    yesButton.addStyleName("btn-primary");

    Button noButton = new Button(constants.no());
    askButtons.add(noButton);
    askButtons.add(yesButton);
    panel.add(askButtons);
    panel.setCellHorizontalAlignment(askButtons, HasAlignment.ALIGN_RIGHT);

    add(panel);

    yesButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        confirmed = true;
        hide();
        procConfirm();
      }
    });

    noButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        confirmed = false;
        hide();
        procConfirm();
      }
    });

    center();
  }

  public boolean confirm() {
    return confirmed;
  }

  public abstract void procConfirm();
}
