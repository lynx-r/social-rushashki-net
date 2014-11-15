package net.rushashki.shashki64.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 14.11.14
 * Time: 0:13
 */
public interface Shashki64ServiceAsync {
    void getName(String name, AsyncCallback<String> async);
}
