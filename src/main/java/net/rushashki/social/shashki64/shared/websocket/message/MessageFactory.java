package net.rushashki.social.shashki64.shared.websocket.message;


import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;
import net.rushashki.social.shashki64.shared.model.Game;
import net.rushashki.social.shashki64.shared.model.GameMessage;
import net.rushashki.social.shashki64.shared.model.Move;
import net.rushashki.social.shashki64.shared.model.Shashist;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 07.12.14
 * Time: 16:37
 */
public interface MessageFactory extends AutoBeanFactory {

  AutoBean<Game> game();

  AutoBean<GameMessage> gameMessage();

  AutoBean<Shashist> shashist();

  AutoBean<Move> move();
}
