package net.rushashki.social.shashki64.server.websocket.game;

import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;
import net.rushashki.social.shashki64.server.service.GameMessageService;
import net.rushashki.social.shashki64.server.service.ShashistService;
import net.rushashki.social.shashki64.server.util.Util;
import net.rushashki.social.shashki64.server.websocket.game.message.GameMessageDecoder;
import net.rushashki.social.shashki64.server.websocket.game.message.GameMessageEncoder;
import net.rushashki.social.shashki64.shared.model.Shashist;
import net.rushashki.social.shashki64.shared.model.entity.GameMessageEntity;
import net.rushashki.social.shashki64.shared.model.entity.ShashistEntity;
import net.rushashki.social.shashki64.shared.websocket.message.MessageFactory;
import net.rushashki.social.shashki64.shared.model.GameMessage;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 07.12.14
 * Time: 12:05
 */
@Singleton
@ServerEndpoint(value = "/ws/game",
    decoders = {GameMessageDecoder.class},
    encoders = {GameMessageEncoder.class}
)
public class GameWebsocket {

  private static Map<Shashist, Session> peers = Collections.synchronizedMap(new HashMap<>());
  private final long MAX_IDLE_TIMEOUT = 1000 * 60 * 15;
  @Inject
  private ShashistService shashistService;
  @Inject
  private GameMessageService gameMessageService;

  @OnOpen
  public void onOpen(Session session) {
    System.out.println("New connection " + session.getId());
    session.setMaxIdleTimeout(MAX_IDLE_TIMEOUT);
  }

  @OnMessage
  public void onMessage(Session session, GameMessage message) {
    switch (message.getMessageType()) {
      case PLAYER_REGISTER:
        handleNewPlayer(message, session);
        break;
      case CHAT_MESSAGE:
        handleChatMessage(session, message);
        break;
      case PLAY_INVITE:
      case PLAY_REJECT_INVITE:
      case PLAY_START:
      case CHAT_PRIVATE_MESSAGE:
        handleChatPrivateMessage(message);
        break;
    }
  }

  private void handleChatMessage(Session session, GameMessage message) {
    session.getOpenSessions().stream().filter(Session::isOpen).forEach(peer -> {
      sendMessage(peer, message);
    });
  }

  private void handleNewPlayer(GameMessage message, Session session) {
    Shashist shashist = message.getSender();
    ShashistEntity shashistEntity = shashistService.find(shashist.getId());

    shashistEntity.setLoggedIn(true);
    shashistEntity.setOnline(true);
    shashistService.edit(shashistEntity);

    shashist.setLoggedIn(true);
    shashist.setOnline(true);

    peers.put(shashist, session);
    System.out.println("Register new game: " + shashist.getSystemId() + " " + session.getId());
    updatePlayerList(session);
  }

  @OnClose
  public void onClose(Session session) {
    Shashist shashist = peers.keySet().stream().filter(sh -> peers.get(sh) == session).findFirst().get();
    ShashistEntity shashistEntity = shashistService.find(shashist.getId());

    shashistEntity.setOnline(false);
    shashistEntity.setPlaying(false);
    shashistService.edit(shashistEntity);

    shashist.setOnline(false);
    shashist.setPlaying(false);

    System.out.println("Disconnected: " + shashist.getSystemId() + " " + session.getId());
    peers.values().remove(session);
    updatePlayerList(session);
  }

  @OnError
  public void onError(Throwable throwable) {
    throwable.printStackTrace();
  }

  private void handleChatPrivateMessage(GameMessage message) {
    Shashist receiver = message.getReceiver();
    Shashist shashist = peers.keySet().stream()
        .filter(s -> s.getSystemId().equals(receiver.getSystemId())).findFirst().get();
    Session session = peers.get(shashist);
    sendMessage(session, message);

    ShashistEntity shashistReceiver = shashistService.find(message.getReceiver().getId());
    ShashistEntity shashistSender = shashistService.find(message.getSender().getId());

    GameMessageEntity gameMessageEntity = new GameMessageEntity();
    gameMessageEntity.setMessageType(message.getMessageType());
    gameMessageEntity.setData(message.getData());
    gameMessageEntity.setMessage(message.getMessage());

    gameMessageEntity.setReceiver(shashistReceiver);
    gameMessageEntity.setSender(shashistSender);

    gameMessageEntity.setSentDate(new Date());

    gameMessageService.create(gameMessageEntity);
  }

  private void updatePlayerList(Session session) {
    MessageFactory messageFactory = AutoBeanFactorySource.create(MessageFactory.class);
    GameMessage gameMessage = messageFactory.gameMessage().as();
    gameMessage.setMessageType(GameMessage.MessageType.USER_LIST_UPDATE);
    gameMessage.setPlayerList(new ArrayList<>(peers.keySet()));
    session.getOpenSessions().stream().filter(Session::isOpen).forEach(peer -> {
      sendMessage(peer, gameMessage);
    });
  }

  private void sendMessage(Session session, GameMessage message) {
    RemoteEndpoint.Basic remote = session.getBasicRemote();
    if (remote != null) {
      try {
        remote.sendText(Util.serializePlayerMessageToJson(message), true);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
