package net.rushashki.shashki64.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import net.rushashki.shashki64.share.model.Message;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 15.11.14
 * Time: 16:37
 */
@RemoteServiceRelativePath("ChatService")
public interface ChatService extends RemoteService {

  public Message getMessage(Message message);

  /**
   * Utility/Convenience class.
   * Use ChatService.App.getInstance() to access static instance of ChatServiceAsync
   */
  public static class App {
    private static final ChatServiceAsync ourInstance = (ChatServiceAsync) GWT.create(ChatService.class);

    public static ChatServiceAsync getInstance() {
      return ourInstance;
    }
  }
}
