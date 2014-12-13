package net.rushashki.shashki64.shared.model;

import java.util.Date;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 12.12.14
 * Time: 9:05
 */
public interface Shashist extends PersistableObject {

  public String getVkUid();

  public void setVkUid(String vkUid);

  public String getEmail();

  public void setEmail(String email);

  public String getFirstName();

  public void setFirstName(String firstName);

  public String getMiddleName();

  public void setMiddleName(String middleName);

  public String getLastName();

  public void setLastName(String lastName);

  public String getPlayerName();

  public void setPlayerName(String playerName);

  public String getAuthProvider();

  public void setAuthProvider(String authProvider);

  public Set<FriendEntity> getFriends();

  public void setFriends(Set<FriendEntity> friends);

  public Set<FriendEntity> getFriendOf();

  public void setFriendOf(Set<FriendEntity> friendOf);

  public boolean isLoggedIn();

  public void setLoggedIn(boolean loggedIn);

  public boolean isPlaying();

  public void setPlaying(boolean playing);

  public boolean isOnline();

  public void setOnline(boolean online);

  public Date getRegisterDate();

  public void setRegisterDate(Date registerDate);

  public Date getLastVisited();

  public void setLastVisited(Date lastVisited);

  public int getVisitCounter();

  public void setVisitCounter(int visitCounter);

  public String getPublicName();

  public String getFullName();

  public String getSessionId();

  public void setSessionId(String sessionId);

  public String getSystemId();

}
