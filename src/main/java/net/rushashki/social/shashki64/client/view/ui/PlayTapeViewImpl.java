package net.rushashki.social.shashki64.client.view.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.social.shashki64.client.component.ui.ShashkiListComponentImpl;
import net.rushashki.social.shashki64.client.view.PlayTapeView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 12:34
 */
public class PlayTapeViewImpl extends BasicViewUi implements PlayTapeView {
  private static Binder ourUiBinder = GWT.create(Binder.class);
  @UiField
  ShashkiListComponentImpl shashkiLenta;

  public PlayTapeViewImpl() {
    initWidget(ourUiBinder.createAndBindUi(this));
  }

  @Override
  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  interface Binder extends UiBinder<HTMLPanel, PlayTapeViewImpl> {
  }
}