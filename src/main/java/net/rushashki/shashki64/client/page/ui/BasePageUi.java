package net.rushashki.shashki64.client.page.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import net.rushashki.shashki64.client.component.NavbarComponent;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.client.page.BasePage;
import org.gwtbootstrap3.client.ui.Container;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 18:23
 */
public class BasePageUi extends Composite implements BasePage {

  @UiField
  NavbarComponent navbar;

  @UiField
  Container container;

  private ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;

  @Override
  public void setWidget(IsWidget w) {
    if (w != null) {
      container.clear();
      container.add(w);
    }
  }

  @Override
  public NavbarComponent getNavbar() {
    return navbar;
  }

  interface BasePageUiBinder extends UiBinder<HTMLPanel, BasePageUi> {
  }

  private static BasePageUiBinder ourUiBinder = GWT.create(BasePageUiBinder.class);

  public BasePageUi() {
    initWidget(ourUiBinder.createAndBindUi(this));
  }
}