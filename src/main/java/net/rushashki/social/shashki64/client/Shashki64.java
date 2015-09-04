package net.rushashki.social.shashki64.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.social.shashki64.client.activity.AppActivityMapper;
import net.rushashki.social.shashki64.client.component.ui.FooterComponentImpl;
import net.rushashki.social.shashki64.client.component.ui.NavbarComponentImpl;
import net.rushashki.social.shashki64.client.config.ShashkiGinjector;
import net.rushashki.social.shashki64.client.event.ClientFactoryEvent;
import net.rushashki.social.shashki64.client.page.BasePage;
import net.rushashki.social.shashki64.client.page.ui.BasePageUi;
import net.rushashki.social.shashki64.client.place.AppPlaceHistoryMapper;
import net.rushashki.social.shashki64.client.place.HomePlace;
import net.rushashki.social.shashki64.client.rpc.ProfileRpcServiceAsync;
import net.rushashki.social.shashki64.client.util.DebugUtils;
import net.rushashki.social.shashki64.shared.model.Shashist;
import net.rushashki.social.shashki64.shared.resources.Resources;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 14.11.14
 * Time: 0:11
 */
public class Shashki64 implements EntryPoint {

  private Image splashImage;

  private HomePlace defaultPlace = new HomePlace("Home");

  private ClientFactory clientFactory = GWT.create(ClientFactory.class);
  private ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  private EventBus eventBus;
  private ProfileRpcServiceAsync profileService;
  private BasePage appWidget = new BasePageUi();

  public void onModuleLoad() {
    DebugUtils.initDebugAndErrorHandling();

    try {
      this.profileService = shashkiGinjector.getProfileService();
      this.eventBus = shashkiGinjector.getEventBus();

      splashImage = new Image(Resources.INSTANCE.images().loadIconImage().getSafeUri());
      splashImage.addStyleName("loader-image");
      RootPanel.get("content").add(splashImage);

      ActivityMapper activityMapper = new AppActivityMapper();
      ActivityManager activityManager = new ActivityManager(activityMapper, shashkiGinjector.getEventBus());
      activityManager.setDisplay(appWidget);

      AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
      PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
      historyHandler.register(shashkiGinjector.getPlaceController(), shashkiGinjector.getEventBus(), defaultPlace);

      RootPanel.get("navigation").add(new NavbarComponentImpl());
      RootPanel.get("footer").add(new FooterComponentImpl());

      profileService.getAuthProfile(new AsyncCallback<Shashist>() {
        @Override
        public void onFailure(Throwable throwable) {

        }

        @Override
        public void onSuccess(Shashist shashist) {
          RootPanel.get("content").remove(splashImage);

          clientFactory.setPlayer(shashist);
          RootPanel.get("content").add((IsWidget) appWidget);
          eventBus.fireEvent(new ClientFactoryEvent(clientFactory));
          historyHandler.handleCurrentHistory();

//        if (shashist != null) {
//          new PlayerWebsocket(clientFactory);
//        }
//          eventBus.fireEvent(new StartPlayEvent(true));

        }
      });
    } catch (RuntimeException e) {
      GWT.log("Error in 'onModuleLoad() method", e);
      e.printStackTrace();
    }
  }
}
