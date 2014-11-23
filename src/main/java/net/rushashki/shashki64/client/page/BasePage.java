package net.rushashki.shashki64.client.page;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import net.rushashki.shashki64.client.component.NavbarComponent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 18:26
 */
public interface BasePage extends AcceptsOneWidget {
  public NavbarComponent getNavbar();
}
