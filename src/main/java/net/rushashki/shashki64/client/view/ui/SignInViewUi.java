package net.rushashki.shashki64.client.view.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.shashki64.client.view.SignInView;

import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 24.11.14
 * Time: 12:37
 */
public class SignInViewUi extends BasicViewUi implements SignInView {
  private static LoginViewUiUiBinder ourUiBinder = GWT.create(LoginViewUiUiBinder.class);

  @UiField
  HTMLPanel signInContainer;

  public SignInViewUi() {
    initWidget(ourUiBinder.createAndBindUi(this));

    Scheduler.get().scheduleFinally(new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        verticalAlignEnterBox();
      }
    });
  }

  private void verticalAlignEnterBox() {
    int windowHeight = Window.getClientHeight();
    int signInHeight = signInContainer.getOffsetHeight();
    logger.warning("" + windowHeight + ":" + signInHeight);
    double containerMarginTop = windowHeight / 2 - signInHeight - 40;
    signInContainer.getElement().getStyle().setMarginTop(containerMarginTop, Style.Unit.PX);
  }

  @Override
  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  interface LoginViewUiUiBinder extends UiBinder<HTMLPanel, SignInViewUi> {
  }
}