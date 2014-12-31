package net.rushashki.social.shashki64.server.rpc;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import net.rushashki.social.shashki64.client.rpc.ProfileRpcService;
import net.rushashki.social.shashki64.server.service.ShashistService;
import net.rushashki.social.shashki64.shared.model.Shashist;
import net.rushashki.social.shashki64.shared.model.entity.ShashistEntity;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 05.12.14
 * Time: 20:03
 */
public class ProfileRpcServiceImpl extends RemoteServiceServlet implements ProfileRpcService {

  @Inject
  private ShashistService shashistService;

  @Override
  public Boolean isAuthenticated() {
    HttpSession session = getThreadLocalRequest().getSession();
    Object isAuth = session.getAttribute("isAuthenticated");
    return isAuth == null ? Boolean.FALSE : (Boolean) isAuth;
  }

  @Override
  public Shashist getProfile(Long profileId) {
    return shashistService.find(profileId);
  }

  @Override
  public Shashist getAuthProfile() {
    HttpSession session = getThreadLocalRequest().getSession();
    if (session != null) {
      return shashistService.findBySessionId(session.getId());
    }
    return null;
  }

  @Override
  public void saveProfile(Shashist profile) {
    ShashistEntity shashistEntity = shashistService.find(profile.getId());
    if (shashistEntity != null) {
      shashistService.edit(shashistEntity.copy(profile));
    }
  }
}