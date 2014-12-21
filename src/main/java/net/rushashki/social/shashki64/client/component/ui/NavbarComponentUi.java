package net.rushashki.social.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.social.shashki64.client.component.NavbarComponent;
import net.rushashki.social.shashki64.client.config.ShashkiGinjector;
import net.rushashki.social.shashki64.client.event.OnClientFactoryEvent;
import net.rushashki.social.shashki64.client.event.OnClientFactoryEventHandler;
import net.rushashki.social.shashki64.client.event.OnNavbarReloadEvent;
import net.rushashki.social.shashki64.client.event.OnNavbarReloadEventHandler;
import net.rushashki.social.shashki64.client.place.*;
import net.rushashki.social.shashki64.client.rpc.ProfileServiceAsync;
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
    private AnchorListItem profileDropDown;

    public NavbarComponentUi() {
        initWidget(ourUiBinder.createAndBindUi(this));

        this.constants = shashkiGinjector.getShashkiConstants();
        this.placeController = shashkiGinjector.getPlaceController();
        this.eventBus = shashkiGinjector.getEventBus();
        this.profileService = shashkiGinjector.getProfileService();

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

        playLentaLink = new AnchorListItem(constants.playLenta());
        playLentaLink.setIcon(IconType.LIST);
        playLentaLink.addClickHandler(event -> {
            disableLink(prevActiveLink);
            prevActiveLink = playLentaLink;
            placeController.goTo(new PlayLentaPlace(constants.playLentaToken()));
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
        eventBus.addHandler(OnNavbarReloadEvent.TYPE, new OnNavbarReloadEventHandler() {
            @Override
            public void onEvent(OnNavbarReloadEvent event) {
                NavbarComponentUi.this.setActive(event.getToken());
            }
        });

        // TODO: Not Compile
        eventBus.addHandler(OnClientFactoryEvent.TYPE, new OnClientFactoryEventHandler() {
            @Override
            public void onOnClientFactory(OnClientFactoryEvent event) {
                Shashist shashist = event.getClientFactory().getPlayer();
                if (shashist != null) {
                    navLeft.add(homeLink);
                    navLeft.add(playLentaLink);
                    navLeft.add(playLink);
                    navRight.add(profileDropDown);
                    profileDropDown.setIcon(IconType.USER);
                    profileDropDown.setText(shashist.getPublicName());
                } else {
                    navLeft.add(homeLink);
                    navLeft.add(playLentaLink);
                    navRight.add(signInLink);
                }
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

    interface NavbarComponentUiUiBinder extends UiBinder<Navbar, NavbarComponentUi> {
    }
}