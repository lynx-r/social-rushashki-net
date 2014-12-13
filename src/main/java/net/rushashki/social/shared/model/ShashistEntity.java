package net.rushashki.social.shared.model;

import com.google.gwt.user.client.rpc.GwtTransient;
import net.rushashki.social.client.util.Util;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 01.12.14
 * Time: 0:36
 */
@Entity
@Table(name = "shashist")
public class ShashistEntity extends GwtPersistableObject implements Shashist {

  private String sessionId;
  private String vkUid;
  @Email
  private String email;
  private String firstName;
  private String middleName;
  private String lastName;
  private String playerName;
  private String authProvider;
  @GwtTransient
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.friendOf", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<FriendEntity> friends;
  @GwtTransient
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.friend", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<FriendEntity> friendOf;
  private boolean loggedIn;
  private boolean playing;
  private boolean online;
  private Date registerDate;
  private Date lastVisited;
  private int visitCounter;
  private String hashId;

  public ShashistEntity() {
  }

  @Override
  public String getSessionId() {
    return sessionId;
  }

  @Override
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ShashistEntity shashist = (ShashistEntity) o;

    if (loggedIn != shashist.loggedIn) return false;
    if (online != shashist.online) return false;
    if (playing != shashist.playing) return false;
    if (visitCounter != shashist.visitCounter) return false;
    if (authProvider != null ? !authProvider.equals(shashist.authProvider) : shashist.authProvider != null)
      return false;
    if (email != null ? !email.equals(shashist.email) : shashist.email != null) return false;
    if (firstName != null ? !firstName.equals(shashist.firstName) : shashist.firstName != null) return false;
    if (friendOf != null ? !friendOf.equals(shashist.friendOf) : shashist.friendOf != null) return false;
    if (friends != null ? !friends.equals(shashist.friends) : shashist.friends != null) return false;
    if (lastName != null ? !lastName.equals(shashist.lastName) : shashist.lastName != null) return false;
    if (lastVisited != null ? !lastVisited.equals(shashist.lastVisited) : shashist.lastVisited != null) return false;
    if (middleName != null ? !middleName.equals(shashist.middleName) : shashist.middleName != null) return false;
    if (playerName != null ? !playerName.equals(shashist.playerName) : shashist.playerName != null) return false;
    if (registerDate != null ? !registerDate.equals(shashist.registerDate) : shashist.registerDate != null)
      return false;
    if (vkUid != null ? !vkUid.equals(shashist.vkUid) : shashist.vkUid != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = vkUid != null ? vkUid.hashCode() : 0;
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
    result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
    result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
    result = 31 * result + (playerName != null ? playerName.hashCode() : 0);
    result = 31 * result + (authProvider != null ? authProvider.hashCode() : 0);
    result = 31 * result + (friends != null ? friends.hashCode() : 0);
    result = 31 * result + (friendOf != null ? friendOf.hashCode() : 0);
    result = 31 * result + (loggedIn ? 1 : 0);
    result = 31 * result + (playing ? 1 : 0);
    result = 31 * result + (online ? 1 : 0);
    result = 31 * result + (registerDate != null ? registerDate.hashCode() : 0);
    result = 31 * result + (lastVisited != null ? lastVisited.hashCode() : 0);
    result = 31 * result + visitCounter;
    return result;
  }

  @Override
  public String getVkUid() {
    return vkUid;
  }

  @Override
  public void setVkUid(String vkUid) {
    this.vkUid = vkUid;
  }

  @Override
  public String getEmail() {
    return email;
  }

  @Override
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String getFirstName() {
    return firstName;
  }

  @Override
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Override
  public String getMiddleName() {
    return middleName;
  }

  @Override
  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public String getPlayerName() {
    return playerName;
  }

  @Override
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  @Override
  public String getAuthProvider() {
    return authProvider;
  }

  @Override
  public void setAuthProvider(String authProvider) {
    this.authProvider = authProvider;
  }

  @Override
  public Set<FriendEntity> getFriends() {
    return friends;
  }

  @Override
  public void setFriends(Set<FriendEntity> friends) {
    this.friends = friends;
  }

  @Override
  public Set<FriendEntity> getFriendOf() {
    return friendOf;
  }

  @Override
  public void setFriendOf(Set<FriendEntity> friendOf) {
    this.friendOf = friendOf;
  }

  @Override
  public boolean isLoggedIn() {
    return loggedIn;
  }

  @Override
  public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }

  @Override
  public boolean isPlaying() {
    return playing;
  }

  @Override
  public void setPlaying(boolean playing) {
    this.playing = playing;
  }

  @Override
  public boolean isOnline() {
    return online;
  }

  @Override
  public void setOnline(boolean online) {
    this.online = online;
  }

  @Override
  public Date getRegisterDate() {
    return registerDate;
  }

  @Override
  public void setRegisterDate(Date registerDate) {
    this.registerDate = registerDate;
  }

  @Override
  public Date getLastVisited() {
    return lastVisited;
  }

  @Override
  public void setLastVisited(Date lastVisited) {
    this.lastVisited = lastVisited;
  }

  @Override
  public int getVisitCounter() {
    return visitCounter;
  }

  @Override
  public void setVisitCounter(int visitCounter) {
    this.visitCounter = visitCounter;
  }

  @Override
  public String getPublicName() {
    return getPlayerName() == null ? getFullName() : getPlayerName();
  }

  @Override
  public String getFullName() {
    return getFirstName() + " " + getLastName();
  }

  @Override
  public String getSystemId() {
    if (hashId == null) {
      String uid = "";
      if (getEmail() != null) {
        uid = getEmail();
      } else if (getVkUid() != null) {
        uid = getVkUid();
      }
      hashId = Util.hashString(uid + ":" + getPublicName());
    }
    return hashId;
  }

}
