package net.rushashki.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.share.locale.ShashkiConstants;
import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.NavbarNav;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 18:29
 */
public class NavbarComponentUi extends Composite {
  interface NavbarComponentUiUiBinder extends UiBinder<HTMLPanel, NavbarComponentUi> {
  }

  private static NavbarComponentUiUiBinder ourUiBinder = GWT.create(NavbarComponentUiUiBinder.class);

  private ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;

  private ShashkiConstants constants;

  @UiField
  NavbarNav navLeft;

  @UiField
  NavbarNav navRight;

  public NavbarComponentUi() {
    initWidget(ourUiBinder.createAndBindUi(this));

    this.constants = shashkiGinjector.getShashkiConstants();

    AnchorListItem homeLink = new AnchorListItem(constants.home());
    navLeft.add(homeLink);
  }
}