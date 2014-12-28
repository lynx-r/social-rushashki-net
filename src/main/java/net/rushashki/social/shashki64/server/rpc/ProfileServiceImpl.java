package net.rushashki.social.shashki64.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.rushashki.social.shashki64.client.rpc.ProfileService;
import net.rushashki.social.shashki64.server.service.ShashistService;
import net.rushashki.social.shashki64.shared.model.Shashist;
import net.rushashki.social.shashki64.shared.model.ShashistEntity;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 05.12.14
 * Time: 20:03
 */
public class ProfileServiceImpl extends RemoteServiceServlet implements ProfileService {

  @Inject
  private ShashistService shashistService;

  @Override
  public Boolean isAuthenticated() {
    HttpSession session = getThreadLocalRequest().getSession();
    Object isAuth = session.getAttribute("isAuthenticated");
    return isAuth == null ? Boolean.FALSE : (Boolean) isAuth;
  }

  @Override
  public Shashist getProfile() {
    HttpSession session = getThreadLocalRequest().getSession();
    if (session != null) {
      return shashistService.findBySessionId(session.getId());
    }
    return null;
  }

  @Override
  public void saveProfile(Shashist profile) {
    ShashistEntity shashistEntity = shashistService.find(profile.getId());
    shashistEntity.setOnline(profile.isOnline());
    shashistEntity.setPlaying(profile.isPlaying());
    shashistEntity.setLoggedIn(profile.isLoggedIn());
    shashistEntity.setPlayerName(profile.getPlayerName());
    shashistService.edit(shashistEntity);
  }
}