package net.rushashki.shashki64.client.config;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.shashki64.client.view.impl.HomeViewImpl;
import net.rushashki.shashki64.client.view.impl.PlayViewImpl;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 13:11
 */
@GinModules(ShashkiGinModule.class)
public interface ShashkiGinjector extends Ginjector {

  public static final ShashkiGinjector INSTANCE = GWT.create(ShashkiGinjector.class);

  EventBus getEventBus();

  PlaceController getPlaceController();

  HomeViewImpl getHomeView();

  PlayViewImpl getPlayView();
}
