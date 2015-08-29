package net.rushashki.social.shashki64.client.config;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.social.shashki64.client.rpc.GameRpcServiceAsync;
import net.rushashki.social.shashki64.client.rpc.GameMessageRpcServiceAsync;
import net.rushashki.social.shashki64.client.rpc.ProfileRpcServiceAsync;
import net.rushashki.social.shashki64.client.util.ShashkiLogger;
import net.rushashki.social.shashki64.client.view.*;
import net.rushashki.social.shashki64.client.view.ui.HomeViewImpl;
import net.rushashki.social.shashki64.client.view.ui.PlayViewImpl;
import net.rushashki.social.shashki64.shared.config.ShashkiConfiguration;
import net.rushashki.social.shashki64.shared.locale.ShashkiConstants;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 13:11
 */
@GinModules(ShashkiGinModule.class)
public interface ShashkiGinjector extends Ginjector {

  public static final ShashkiGinjector INSTANCE = GWT.create(ShashkiGinjector.class);

  ShashkiLogger getLogger();

  EventBus getEventBus();

  PlaceController getPlaceController();

  ShashkiConstants getShashkiConstants();

  ShashkiConfiguration getShashkiConfiguration();


  HomeViewImpl getHomeView();

  PlayTapeView getPlayTapeView();

  PlayViewImpl getPlayView();

  SignInView getSignInView();

  ProfileView getProfileView();

  SettingsView getSettingsView();

  AboutUsView getAboutUsView();

  NotFoundView getNotFoundView();


  ProfileRpcServiceAsync getProfileService();

  GameMessageRpcServiceAsync getGameMessageService();

  GameRpcServiceAsync getGameService();

}