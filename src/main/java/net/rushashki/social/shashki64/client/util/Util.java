package net.rushashki.social.shashki64.client.util;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import net.rushashki.social.shashki64.shared.websocket.message.MessageFactory;
import net.rushashki.social.shashki64.shared.model.GameMessage;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 07.12.14
 * Time: 22:58
 */
public class Util {

  private MessageFactory messageFactory = GWT.create(MessageFactory.class);
  public static final String MESSAGE_SEPARATOR = " > ";

  public GameMessage makePlayerMessage() {
    AutoBean<GameMessage> playerMessageAutoBean = messageFactory.gameMessage();
    return playerMessageAutoBean.as();
  }

  public static String serializePlayerMessageToJson(GameMessage message) {
    MessageFactory chatFactory = GWT.create(MessageFactory.class);
    AutoBean<GameMessage> bean = chatFactory.create(GameMessage.class, message);
    return AutoBeanCodex.encode(bean).getPayload();
  }

  public static String hashString(String in) {
    MessageDigest messageDigest;
    try {
      messageDigest = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return "";
    }
    byte bDigest[] = messageDigest.digest(in.getBytes());
    BigInteger bi = new BigInteger(bDigest);
    return bi.toString(16);
  }


  public static GameMessage deserializeFromJson(String json) {
    MessageFactory messageFactory = GWT.create(MessageFactory.class);
    AutoBean<GameMessage> bean = AutoBeanCodex.decode(messageFactory, GameMessage.class, json);
    return bean.as();
  }

}
