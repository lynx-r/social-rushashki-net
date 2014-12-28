package net.rushashki.social.shashki64.shared.locale;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.safehtml.shared.SafeHtml;
import net.rushashki.social.shashki64.shared.model.Shashist;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 20:13
 */
public interface ShashkiConstants extends Messages {

  String home();

  String homeToken();

  String play();

  String playToken();

  String unrecognizedPlace();

  String signIn();

  String signInToken();

  String logout();

  String jumbotronGreeting();

  String jumbotronSubGreeting();

  String playTape();

  String playTapeToken();

  String profile();

  String profileToken();

  String myPage();

  String settings();

  String settingsToken();

  String webSocketDoesNotSupport();

  String reconnect();

  String close();

  String error();

  String info();

  String captionGame();

  String next();

  String cancel();

  String chooseYourColor();

  String white();

  String black();

  String waitResponse();

  String yes();

  String no();

  String confirm();

  String inviteMessage(String inviting, String color);

  String inviteToPlay(String opponent, String playType);

  String draughts();

  String atFirstStartPlay();

  String aboutUs();

  String aboutUsToken();

  String profileUpdated();

  String errorWhileProfileUpdate();

  String tooLongPlayerName();
}
