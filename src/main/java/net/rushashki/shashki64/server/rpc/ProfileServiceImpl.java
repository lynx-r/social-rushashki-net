package net.rushashki.shashki64.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.rushashki.shashki64.client.rpc.ProfileService;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 05.12.14
 * Time: 20:03
 */
public class ProfileServiceImpl extends RemoteServiceServlet implements ProfileService {
  @Override
  public Boolean isAuthenticated() {
    HttpSession session = getThreadLocalRequest().getSession();
    Object isAuth = session.getAttribute("isAuthenticated");
    return isAuth == null ? Boolean.FALSE : (Boolean) isAuth;
  }
}