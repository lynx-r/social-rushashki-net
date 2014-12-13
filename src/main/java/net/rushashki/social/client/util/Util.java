package net.rushashki.social.client.util;

import com.google.gwt.core.client.GWT;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import net.rushashki.social.shared.websocket.message.MessageFactory;
import net.rushashki.social.shared.websocket.message.PlayerMessage;

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

  public PlayerMessage makePlayerMessage() {
    AutoBean<PlayerMessage> playerMessageAutoBean = messageFactory.playerMessage();
    return playerMessageAutoBean.as();
  }

  public static String serializePlayerMessageToJson(PlayerMessage message) {
    MessageFactory chatFactory = GWT.create(MessageFactory.class);
    AutoBean<PlayerMessage> bean = chatFactory.create(PlayerMessage.class, message);
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


  public static PlayerMessage deserializeFromJson(String json) {
    MessageFactory messageFactory = GWT.create(MessageFactory.class);
    AutoBean<PlayerMessage> bean = AutoBeanCodex.decode(messageFactory, PlayerMessage.class, json);
    return bean.as();
  }

}
