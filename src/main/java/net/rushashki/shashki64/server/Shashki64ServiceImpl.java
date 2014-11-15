package net.rushashki.shashki64.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.rushashki.shashki64.client.Shashki64Service;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 14.11.14
 * Time: 0:13
 */
public class Shashki64ServiceImpl extends RemoteServiceServlet implements Shashki64Service {
    @Override
    public String getName(String name) {
        return "Hi " + name + "!";
    }
}