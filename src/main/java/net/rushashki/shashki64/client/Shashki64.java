package net.rushashki.shashki64.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import net.rushashki.shashki64.client.activity.AppActivityMapper;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.client.place.AppPlaceHistoryMapper;
import net.rushashki.shashki64.client.place.HomePlace;
import net.rushashki.shashki64.share.resources.Resources;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 14.11.14
 * Time: 0:11
 */
public class Shashki64 implements EntryPoint {

  private Image splashImage;

  private HomePlace defaultPlace = new HomePlace("World!");

  private ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;

  private SimplePanel appWidget = new SimplePanel();

  public void onModuleLoad() {
    splashImage = new Image(Resources.INSTANCE.images().loadIconImage().getSafeUri());
    splashImage.addStyleName("loader-image");
    RootPanel.get("content").add(splashImage);

    new Timer() {
      @Override
      public void run() {
        ActivityMapper activityMapper = new AppActivityMapper();
        ActivityManager activityManager = new ActivityManager(activityMapper, shashkiGinjector.getEventBus());
        activityManager.setDisplay(appWidget);

        AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(shashkiGinjector.getPlaceController(), shashkiGinjector.getEventBus(), defaultPlace);

        RootPanel.get("content").remove(splashImage);
        RootPanel.get("content").add(appWidget);

        historyHandler.handleCurrentHistory();
      }
    }.schedule(1000);
  }
}
