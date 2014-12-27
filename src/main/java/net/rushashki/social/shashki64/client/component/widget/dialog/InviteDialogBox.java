package net.rushashki.social.shashki64.client.component.widget.dialog;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import net.rushashki.social.shashki64.shared.resources.Resources;
import org.gwtbootstrap3.client.ui.*;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 09.02.14
 * Time: 21:15
 */
public abstract class InviteDialogBox extends BasicDialogBox {
  private final Label inviteLabel = new Label();
  private final RadioButton whiteRadio;
  private final Button submitButton;
  private final Button cancelButton;
  private final HorizontalPanel waitMessage = new HorizontalPanel();
  private Timer waitResponseTimer;

  private int waitCounter = 0;
  private final int WAIT = 29;
  private Timer timerCounterTimer;
  private Label waitMessageLabel;

  public InviteDialogBox() {
    setText(constants.captionGame());
    VerticalPanel panel = new VerticalPanel();
    panel.add(inviteLabel);

    submitButton = new Button(constants.next());
    cancelButton = new Button(constants.cancel());

    Label label = new Label(constants.chooseYourColor());
    panel.add(label);


    ButtonGroup group = new ButtonGroup();
    whiteRadio = new RadioButton(constants.white());
    RadioButton blackRadio = new RadioButton(constants.black());
    whiteRadio.setValue(true);

    group.add(whiteRadio);
    group.add(blackRadio);
    panel.add(group);

    final Image waitImage = new Image(Resources.INSTANCE.images().busyIconImage());
    waitMessage.setVisible(false);
    waitMessage.add(waitImage);
    waitMessageLabel = new Label(constants.waitResponse());
    waitMessageLabel.setHeight("32px");
    waitMessageLabel.getElement().getStyle().setPaddingLeft(5, Style.Unit.PX);
    waitMessageLabel.getElement().getStyle().setVerticalAlign(Style.VerticalAlign.MIDDLE);
    waitMessageLabel.getElement().getStyle().setProperty("display", "inherit");
    waitMessage.add(waitMessageLabel);

    waitCounter = WAIT;
    timerCounterTimer = new Timer() {
      @Override
      public void run() {
        waitMessageLabel.setText(constants.waitResponse() + " " + waitCounter);
        waitCounter -= 1;
      }
    };
    panel.add(waitMessage);

    group = new ButtonGroup();
    group.add(cancelButton);
    group.add(submitButton);
    submitButton.addStyleName("btn-primary");

    panel.add(group);
    panel.setCellHorizontalAlignment(group, HasAlignment.ALIGN_RIGHT);

    add(panel);

    waitResponseTimer = new Timer() {
      @Override
      public void run() {
        hide();
        stopTimers();
      }
    };

    submitButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        waitMessage.setVisible(true);
        waitResponseTimer.schedule((WAIT + 1) * 1000);
        timerCounterTimer.scheduleRepeating(1000);
        submitted(whiteRadio.getValue());
        submitButton.setEnabled(false);
      }
    });
    cancelButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        hide();
        canceled();
        timerCounterTimer.cancel();
        waitResponseTimer.cancel();
        submitButton.setEnabled(true);
        waitMessageLabel.setText(constants.waitResponse());
        waitMessage.setVisible(false);
        waitCounter = WAIT;
      }
    });
  }

  public void show(String message) {
    inviteLabel.setText(message);
    show();
    center();
  }

  @Override
  public void hide() {
    super.hide();
    stopTimers();
  }

  private void stopTimers() {
    timerCounterTimer.cancel();
    waitMessageLabel.setText(constants.waitResponse());
    waitCounter = WAIT;
    submitButton.setEnabled(true);
    waitMessage.setVisible(false);
  }

  public abstract void submitted(boolean white);

  public abstract void canceled();
}
