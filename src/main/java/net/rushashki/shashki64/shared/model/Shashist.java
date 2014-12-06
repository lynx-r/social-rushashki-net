package net.rushashki.shashki64.shared.model;

import com.google.gwt.user.client.rpc.GwtTransient;
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
public class Shashist extends GwtPersistableObject {

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
  private Set<Friend> friends;
  @GwtTransient
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.friend", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Friend> friendOf;
  private boolean loggedIn;
  private boolean playing;
  private boolean online;
  private Date registerDate;
  private Date lastVisited;
  private int visitCounter;

  public Shashist() {
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Shashist shashist = (Shashist) o;

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

  public String getVkUid() {
    return vkUid;
  }

  public void setVkUid(String vkUid) {
    this.vkUid = vkUid;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public String getAuthProvider() {
    return authProvider;
  }

  public void setAuthProvider(String authProvider) {
    this.authProvider = authProvider;
  }

  public Set<Friend> getFriends() {
    return friends;
  }

  public void setFriends(Set<Friend> friends) {
    this.friends = friends;
  }

  public Set<Friend> getFriendOf() {
    return friendOf;
  }

  public void setFriendOf(Set<Friend> friendOf) {
    this.friendOf = friendOf;
  }

  public boolean isLoggedIn() {
    return loggedIn;
  }

  public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;
  }

  public boolean isPlaying() {
    return playing;
  }

  public void setPlaying(boolean playing) {
    this.playing = playing;
  }

  public boolean isOnline() {
    return online;
  }

  public void setOnline(boolean online) {
    this.online = online;
  }

  public Date getRegisterDate() {
    return registerDate;
  }

  public void setRegisterDate(Date registerDate) {
    this.registerDate = registerDate;
  }

  public Date getLastVisited() {
    return lastVisited;
  }

  public void setLastVisited(Date lastVisited) {
    this.lastVisited = lastVisited;
  }

  public int getVisitCounter() {
    return visitCounter;
  }

  public void setVisitCounter(int visitCounter) {
    this.visitCounter = visitCounter;
  }

  public String getPublicName() {
    return playerName == null ? firstName : playerName;
  }
}
