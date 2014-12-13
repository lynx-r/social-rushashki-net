package net.rushashki.shashki64.server.websocket.player;

import com.google.web.bindery.autobean.vm.AutoBeanFactorySource;
import net.rushashki.shashki64.server.service.ShashistService;
import net.rushashki.shashki64.server.util.Util;
import net.rushashki.shashki64.server.websocket.player.message.PlayerMessageDecoder;
import net.rushashki.shashki64.server.websocket.player.message.PlayerMessageEncoder;
import net.rushashki.shashki64.shared.model.Shashist;
import net.rushashki.shashki64.shared.model.ShashistEntity;
import net.rushashki.shashki64.shared.websocket.message.MessageFactory;
import net.rushashki.shashki64.shared.websocket.message.PlayerMessage;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 07.12.14
 * Time: 12:05
 */
@Singleton
@ServerEndpoint(value = "/ws/echo",
    decoders = {PlayerMessageDecoder.class},
    encoders = {PlayerMessageEncoder.class})
public class PlayerWebsocket {

  @Inject
  private ShashistService shashistService;

  private static Map<Shashist, Session> peers = Collections.synchronizedMap(new HashMap<>());
  private long MAX_IDLE_TIMEOUT = 1000 * 60 * 1;

  @OnOpen
  public void onOpen(Session session) {
    System.out.println("New connection");
  }

  @OnMessage
  public void onMessage(Session session, PlayerMessage message) {
    switch (message.getType()) {
      case REGISTER_PLAYER:
        handleNewPlayer(message, session);
        break;
      case DISCONNECT_PLAYER:
        handleDisconnectPlayer(message);
      case USER_LIST_UPDATE:
        handleUpdatePlayerList();
        break;
      case CHAT_MESSAGE:
        handleChatMessage(message);
        break;
    }
  }

  private void handleDisconnectPlayer(PlayerMessage message) {
    Shashist shashist = message.getSender();

    ShashistEntity shashistEntity = shashistService.find(shashist.getId());
    shashistEntity.setOnline(false);
    shashistEntity.setPlaying(false);
    shashistService.edit(shashistEntity);

    shashist.setOnline(false);
    shashist.setPlaying(false);

    updatePlayerList();

    peers.remove(message.getSender());
  }

  private void handleNewPlayer(PlayerMessage message, Session session) {
    session.setMaxIdleTimeout(MAX_IDLE_TIMEOUT);

    Shashist shashist = message.getSender();
    ShashistEntity shashistEntity = shashistService.find(shashist.getId());

    shashistEntity.setLoggedIn(true);
    shashistEntity.setOnline(true);
    shashistService.edit(shashistEntity);

    shashist.setLoggedIn(true);
    shashist.setOnline(true);

    peers.put(shashist, session);
    updatePlayerList();
  }

  @OnClose
  public void onClose(Session session) {
    System.out.println("User disconnected");

    Shashist shashist = peers.keySet().stream().filter(sh -> peers.get(sh) == session).findFirst().get();
    ShashistEntity shashistEntity = shashistService.find(shashist.getId());

    shashistEntity.setOnline(false);
    shashistEntity.setPlaying(false);
    shashistService.edit(shashistEntity);

    shashist.setOnline(false);
    shashist.setPlaying(false);

    updatePlayerList();

    peers.values().remove(session);
  }

  private void handleUpdatePlayerList() {
    updatePlayerList();
  }

  private void handleChatMessage(PlayerMessage message) {
    Shashist receiver = message.getReceiver();
    Session session = peers.get(receiver);
    sendMessage(session, message);
  }

  private void updatePlayerList() {
    MessageFactory messageFactory = AutoBeanFactorySource.create(MessageFactory.class);
    PlayerMessage playerMessage = messageFactory.playerMessage().as();
    playerMessage.setType(PlayerMessage.MessageType.USER_LIST_UPDATE);
    playerMessage.setPlayerList(new ArrayList<>(peers.keySet()));
    for (Session peer : peers.values()) {
      sendMessage(peer, playerMessage);
    }
  }

  private void sendMessage(Session session, PlayerMessage message) {
    RemoteEndpoint.Basic remote = session.getBasicRemote();
    if (remote != null) {
      try {
        remote.sendText(Util.serializePlayerMessageToJson(message));
      } catch (IOException ioe) {
        System.out.println(ioe.getMessage());
      }
    }
  }

}
