package net.rushashki.social.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.social.shashki64.client.component.NavbarComponent;
import net.rushashki.social.shashki64.client.config.ShashkiGinjector;
import net.rushashki.social.shashki64.client.event.ClientFactoryEvent;
import net.rushashki.social.shashki64.client.event.ClientFactoryEventHandler;
import net.rushashki.social.shashki64.client.event.NavbarReloadEvent;
import net.rushashki.social.shashki64.client.event.NavbarReloadEventHandler;
import net.rushashki.social.shashki64.client.place.*;
import net.rushashki.social.shashki64.shared.locale.ShashkiConstants;
import net.rushashki.social.shashki64.shared.model.Shashist;
import org.gwtbootstrap3.client.ui.*;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Toggle;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 18:29
 */
public class NavbarComponentImpl extends Composite implements NavbarComponent {
  private static Binder ourUiBinder = GWT.create(Binder.class);
  @UiField
  NavbarNav navLeft;
  @UiField
  NavbarNav navRight;
  private AnchorListItem prevActiveLink = null;
  private ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  private PlaceController placeController;
  private ShashkiConstants constants;
  private EventBus eventBus;
  private AnchorListItem homeLink;
  private AnchorListItem playLink;
  private AnchorListItem signInLink;
  private AnchorListItem playTapeLink;
  private AnchorListItem profileLink;
  private AnchorListItem settingsLink;
  private AnchorListItem profileDropDown;
  private AnchorListItem aboutUsLink;

  public NavbarComponentImpl() {
    initWidget(ourUiBinder.createAndBindUi(this));

    this.constants = shashkiGinjector.getShashkiConstants();
    this.placeController = shashkiGinjector.getPlaceController();
    this.eventBus = shashkiGinjector.getEventBus();

    addLinks();
    addEvents();
  }

  private void addLinks() {
    homeLink = new AnchorListItem(constants.home());
    homeLink.setIcon(IconType.HOME);
    homeLink.addClickHandler(event -> {
      disableLink(prevActiveLink);
      prevActiveLink = homeLink;
      placeController.goTo(new HomePlace(constants.homeToken()));
    });

    playTapeLink = new AnchorListItem(constants.playTape());
    playTapeLink.setIcon(IconType.HOME);
    playTapeLink.addClickHandler(event -> {
      disableLink(prevActiveLink);
      prevActiveLink = playTapeLink;
      placeController.goTo(new PlayTapePlace(constants.playTapeToken()));
    });

    aboutUsLink = new AnchorListItem(constants.aboutUs());
    aboutUsLink.setIcon(IconType.INFO_CIRCLE);
    aboutUsLink.addClickHandler(event -> {
      disableLink(prevActiveLink);
      prevActiveLink = aboutUsLink;
      placeController.goTo(new AboutUsPlace(constants.aboutUsToken()));
    });

    playLink = new AnchorListItem(constants.play());
    playLink.setIcon(IconType.PLAY);
    playLink.addClickHandler(event -> {
      disableLink(prevActiveLink);
      prevActiveLink = playLink;
      placeController.goTo(new PlayPlace(constants.playToken()));
    });

    profileLink = new AnchorListItem(constants.myPage());
    profileLink.setIcon(IconType.CIRCLE_THIN);
    profileLink.addClickHandler(event -> {
      disableLink(prevActiveLink);
      prevActiveLink = profileLink;
      placeController.goTo(new ProfilePlace(constants.profileToken()));
    });

    settingsLink = new AnchorListItem(constants.settings());
    settingsLink.setIcon(IconType.COG);
    settingsLink.addClickHandler(event -> {
      disableLink(prevActiveLink);
      prevActiveLink = settingsLink;
      placeController.goTo(new SettingsPlace(constants.settingsToken()));
    });

    signInLink = new AnchorListItem(constants.signIn());
    signInLink.setIcon(IconType.SIGN_IN);
    signInLink.addClickHandler(event -> {
      disableLink(prevActiveLink);
      prevActiveLink = signInLink;
      placeController.goTo(new SignInPlace(constants.signInToken()));
    });

    AnchorListItem logoutLink = new AnchorListItem(constants.logout());
    logoutLink.setHref("/logout");

    profileDropDown = new AnchorListItem();
    profileDropDown.setDataToggle(Toggle.DROPDOWN);

    DropDownMenu dropDownMenu = new DropDownMenu();
    dropDownMenu.add(profileLink);
    dropDownMenu.add(settingsLink);
    dropDownMenu.add(new Divider());
    dropDownMenu.add(logoutLink);

    profileDropDown.add(dropDownMenu);
  }

  private void addEvents() {
    // TODO: Not Compile
    eventBus.addHandler(NavbarReloadEvent.TYPE, new NavbarReloadEventHandler() {
      @Override
      public void onEvent(NavbarReloadEvent event) {
        NavbarComponentImpl.this.setActive(event.getToken());
      }
    });

    // TODO: Not Compile
    eventBus.addHandler(ClientFactoryEvent.TYPE, new ClientFactoryEventHandler() {
      @Override
      public void onClientFactory(ClientFactoryEvent event) {
        Shashist shashist = event.getClientFactory().getPlayer();
        if (shashist != null) {
//                    navLeft.add(homeLink);
          navLeft.add(playTapeLink);
//          navLeft.add(playLink);
//                    navLeft.add(aboutUsLink);
          navRight.add(profileDropDown);
          profileDropDown.setIcon(IconType.USER);
          profileDropDown.setText(shashist.getPublicName());
        } else {
//                    navLeft.add(homeLink);
          navLeft.add(playTapeLink);
//                    navLeft.add(aboutUsLink);
          navRight.add(signInLink);
        }
      }
    });
  }

  private void setActive(String token) {
    if (prevActiveLink == null) {
      if (token.equals(constants.homeToken())) {
        prevActiveLink = homeLink;
      } else if (token.equals(constants.playTapeToken())) {
        prevActiveLink = playTapeLink;
      } else if (token.equals(constants.playToken())) {
        prevActiveLink = playLink;
      } else if (token.equals(constants.aboutUsToken())) {
        prevActiveLink = aboutUsLink;
      } else if (token.equals(constants.signInToken())) {
        prevActiveLink = signInLink;
      } else if (token.equals(constants.profileToken())) {
        prevActiveLink = profileLink;
      } else if (token.equals(constants.settingsToken())) {
        prevActiveLink = settingsLink;
      } else {
        prevActiveLink = playTapeLink;
      }
    }
    if (prevActiveLink == profileLink || prevActiveLink == settingsLink) {
      activateLink(profileDropDown);
    } else {
      disableLink(profileDropDown);
    }
    prevActiveLink.setActive(true);
  }

  private void disableLink(AnchorListItem link) {
    if (link != null) {
      link.setActive(false);
    }
  }

  private void activateLink(AnchorListItem link) {
    if (link != null) {
      link.setActive(true);
    }
  }

  interface Binder extends UiBinder<Navbar, NavbarComponentImpl> {
  }
}
