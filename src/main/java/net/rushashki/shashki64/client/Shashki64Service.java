package net.rushashki.shashki64.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 14.11.14
 * Time: 0:13
 */
@RemoteServiceRelativePath("Shashki64Service")
public interface Shashki64Service extends RemoteService {

    public String getName(String name);

    /**
     * Utility/Convenience class.
     * Use Shashki64Service.App.getInstance() to access static instance of Shashki64ServiceAsync
     */
    public static class App {
        private static final Shashki64ServiceAsync ourInstance = (Shashki64ServiceAsync) GWT.create(Shashki64Service.class);

        public static Shashki64ServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
