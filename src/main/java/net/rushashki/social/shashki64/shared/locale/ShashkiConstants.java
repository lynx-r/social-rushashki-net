package net.rushashki.social.shashki64.shared.locale;

import com.google.gwt.i18n.client.Messages;

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

  String playStartDescription();

  String playRestartDescription();

  String invalidCharsInName();

  String tooShortPlayerName();

  String playerRejectedPlayRequest(String sender);

  String failedToStartGame();

  String errorWhileGettingProfile();

  String errorWhileGettingGame();

  String selectPlayer();

  String selectAnotherPlayerItsYou();

  String yourTurn();

  String opponentTurn();

  String youDisconnected();

  String whites();

  String blacks();

  String youLose();

  String youWon();

  String playDidNotStart();

  String areYouSureYouWantSurrender();

  String opponentSurrendered();

  String doYouWantToProposeDraw();

  String errorWhileSavingGame();

  String playerProposesDraw(String senderName);

  String playerRejectedDraw(String senderName);

  String doYouWantToCancelMove();

  String playerProposesCancelMove(String publicName);

  String playerRejectedMoveCancel(String publicName);

  String youDontMove();

  String opponentLeftGame();
}
