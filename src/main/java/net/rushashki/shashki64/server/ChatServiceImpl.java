package net.rushashki.shashki64.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.rushashki.shashki64.client.ChatService;
import net.rushashki.shashki64.server.dao.MessageDao;
import net.rushashki.shashki64.share.model.Message;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 15.11.14
 * Time: 16:37
 */
public class ChatServiceImpl extends RemoteServiceServlet implements ChatService {
  @Inject
  private MessageDao messageDao;

  @Override
  public Message getMessage(Message message) {
    message.setMessage(message.getMessage() + " Hi from server!");
    messageDao.create(message);
    return messageDao.find(message.getId());
  }
}