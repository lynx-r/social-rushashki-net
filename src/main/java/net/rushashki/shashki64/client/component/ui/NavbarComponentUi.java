package net.rushashki.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.shashki64.client.component.NavbarComponent;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.client.event.NavbarReloadEvent;
import net.rushashki.shashki64.client.event.NavbarReloadEventHandler;
import net.rushashki.shashki64.client.place.HomePlace;
import net.rushashki.shashki64.client.place.PlayLentaPlace;
import net.rushashki.shashki64.client.place.PlayPlace;
import net.rushashki.shashki64.client.place.SignInPlace;
import net.rushashki.shashki64.shared.locale.ShashkiConstants;
import org.gwtbootstrap3.client.ui.AnchorListItem;
import org.gwtbootstrap3.client.ui.Navbar;
import org.gwtbootstrap3.client.ui.NavbarNav;
import org.gwtbootstrap3.client.ui.constants.IconType;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 18:29
 */
public class NavbarComponentUi extends Composite implements NavbarComponent {
  private static NavbarComponentUiUiBinder ourUiBinder = GWT.create(NavbarComponentUiUiBinder.class);
  private AnchorListItem prevActiveLink = null;
  @UiField
  NavbarNav navLeft;
  @UiField
  NavbarNav navRight;

  private ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  private PlaceController placeController;
  private ShashkiConstants constants;
  private EventBus eventBus;
  private AnchorListItem homeLink;
  private AnchorListItem playLink;
  private AnchorListItem signInLink;
  private AnchorListItem playLentaLink;

  public NavbarComponentUi() {
    initWidget(ourUiBinder.createAndBindUi(this));

    this.constants = shashkiGinjector.getShashkiConstants();
    this.placeController = shashkiGinjector.getPlaceController();
    this.eventBus = shashkiGinjector.getEventBus();
    eventBus.addHandler(NavbarReloadEvent.TYPE, event -> setActive(event.getToken()));

    homeLink = new AnchorListItem(constants.home());
    homeLink.setIcon(IconType.HOME);
    homeLink.addClickHandler(event -> {
      disableLink(prevActiveLink);
      prevActiveLink = homeLink;
      placeController.goTo(new HomePlace(constants.homeToken()));
    });
    navLeft.add(homeLink);

    playLentaLink = new AnchorListItem(constants.playLenta());
    playLentaLink.setIcon(IconType.LIST);
    playLentaLink.addClickHandler(event -> {
      disableLink(prevActiveLink);
      prevActiveLink = playLentaLink;
      placeController.goTo(new PlayLentaPlace(constants.playLentaToken()));
    });
    navLeft.add(playLentaLink);

    playLink = new AnchorListItem(constants.play());
    playLink.setIcon(IconType.PLAY);
    playLink.addClickHandler(event -> {
      disableLink(prevActiveLink);
      prevActiveLink = playLink;
      placeController.goTo(new PlayPlace(constants.playToken()));
    });
    navLeft.add(playLink);

    signInLink = new AnchorListItem(constants.login());
    signInLink.setIcon(IconType.SIGN_IN);
    signInLink.addClickHandler(event -> {
      disableLink(prevActiveLink);
      prevActiveLink = signInLink;
      placeController.goTo(new SignInPlace(constants.signInToken()));
    });
    navRight.add(signInLink);
  }

  private void setActive(String token) {
    if (prevActiveLink == null) {
      if (token.equals(constants.homeToken())) {
        prevActiveLink = homeLink;
      } else if (token.equals(constants.playLentaToken())) {
        prevActiveLink = playLentaLink;
      } else if (token.equals(constants.playToken())) {
        prevActiveLink = playLink;
      } else if (token.equals(constants.signInToken())) {
        prevActiveLink = signInLink;
      } else {
        prevActiveLink = homeLink;
      }
    }
    prevActiveLink.setActive(true);
  }

  private void disableLink(AnchorListItem link) {
    if (link != null) {
      link.setActive(false);
    }
  }

  interface NavbarComponentUiUiBinder extends UiBinder<Navbar, NavbarComponentUi> {
  }
}