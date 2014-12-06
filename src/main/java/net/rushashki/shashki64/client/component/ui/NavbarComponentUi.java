package net.rushashki.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.shashki64.client.component.NavbarComponent;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.client.event.OnGetProfileEvent;
import net.rushashki.shashki64.client.event.OnNavbarReloadEvent;
import net.rushashki.shashki64.client.place.*;
import net.rushashki.shashki64.client.rpc.ProfileServiceAsync;
import net.rushashki.shashki64.shared.locale.ShashkiConstants;
import net.rushashki.shashki64.shared.model.Shashist;
import org.gwtbootstrap3.client.ui.*;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Toggle;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 18:29
 */
public class NavbarComponentUi extends Composite implements NavbarComponent {
  private static NavbarComponentUiUiBinder ourUiBinder = GWT.create(NavbarComponentUiUiBinder.class);
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
  private AnchorListItem playLentaLink;
  private AnchorListItem profileLink;
  private AnchorListItem settingsLink;
  private ProfileServiceAsync profileService;

  public NavbarComponentUi() {
    initWidget(ourUiBinder.createAndBindUi(this));

    this.constants = shashkiGinjector.getShashkiConstants();
    this.placeController = shashkiGinjector.getPlaceController();
    this.eventBus = shashkiGinjector.getEventBus();
    this.profileService = shashkiGinjector.getProfileService();

    eventBus.addHandler(OnNavbarReloadEvent.TYPE, event -> setActive(event.getToken()));

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

    eventBus.addHandler(OnGetProfileEvent.TYPE, profileEvent -> {
      Shashist shashist = profileEvent.getProfile();
      if (shashist != null) {
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

        AnchorListItem logoutLink = new AnchorListItem(constants.logout());
        logoutLink.setHref("/logout");

        AnchorListItem profileDropDown = new AnchorListItem(shashist.getName());
        profileDropDown.setIcon(IconType.USER);
        profileDropDown.setDataToggle(Toggle.DROPDOWN);

        DropDownMenu dropDownMenu = new DropDownMenu();
        dropDownMenu.add(profileLink);
        dropDownMenu.add(settingsLink);
        dropDownMenu.add(new Divider());
        dropDownMenu.add(logoutLink);

        profileDropDown.add(dropDownMenu);

        navRight.add(profileDropDown);
      } else {
        signInLink = new AnchorListItem(constants.signIn());
        signInLink.setIcon(IconType.SIGN_IN);
        signInLink.addClickHandler(event -> {
          disableLink(prevActiveLink);
          prevActiveLink = signInLink;
          placeController.goTo(new SignInPlace(constants.signInToken()));
        });

        navRight.add(signInLink);
      }
    });
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
      } else if (token.equals(constants.profileToken())) {
        prevActiveLink = profileLink;
      } else if (token.equals(constants.settingsToken())) {
        prevActiveLink = settingsLink;
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