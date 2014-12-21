package net.rushashki.social.shashki64.client.page.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.social.shashki64.client.ClientFactory;
import net.rushashki.social.shashki64.client.config.ShashkiGinjector;
import net.rushashki.social.shashki64.client.page.BasePage;
import net.rushashki.social.shashki64.client.view.BasicView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 18:23
 */
public class BasePageUi extends Composite implements BasePage {

  private static BasePageUiBinder ourUiBinder = GWT.create(BasePageUiBinder.class);
  @UiField
  HTMLPanel container;
  private ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  private EventBus eventBus;

  public BasePageUi() {
    initWidget(ourUiBinder.createAndBindUi(this));
    eventBus = shashkiGinjector.getEventBus();
  }

  @Override
  public void setWidget(IsWidget w) {
    if (w != null) {
      container.clear();
      container.add(w);
    }
  }

  interface BasePageUiBinder extends UiBinder<HTMLPanel, BasePageUi> {
  }
}