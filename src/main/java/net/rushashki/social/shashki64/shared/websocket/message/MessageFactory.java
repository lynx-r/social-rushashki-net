package net.rushashki.social.shashki64.shared.websocket.message;


import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import net.rushashki.social.shashki64.shared.model.Shashist;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 07.12.14
 * Time: 16:37
 */
public interface MessageFactory extends AutoBeanFactory {
  AutoBean<PlayerMessage> playerMessage();
  AutoBean<Shashist> shashist();
}
