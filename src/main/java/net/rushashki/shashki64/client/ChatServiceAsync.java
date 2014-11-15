package net.rushashki.shashki64.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import net.rushashki.shashki64.share.model.Message;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 15.11.14
 * Time: 16:37
 */
public interface ChatServiceAsync {

  void getMessage(Message message, AsyncCallback<Message> async);
}
