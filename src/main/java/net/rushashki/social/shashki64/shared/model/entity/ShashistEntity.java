package net.rushashki.social.shashki64.shared.model.entity;

import com.google.gwt.user.client.rpc.GwtTransient;
import net.rushashki.social.shashki64.client.util.Util;
import net.rushashki.social.shashki64.shared.model.Shashist;
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
public class ShashistEntity extends PersistableObjectImpl implements Shashist {

  @Column(name = "session_id")
  private String sessionId;
  @Column(name = "vk_uid")
  private String vkUid;
  @Email
  private String email;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "player_name")
  private String playerName;
  @Column(name = "auth_provider")
  private String authProvider;

  @GwtTransient
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.friendOf", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<FriendEntity> friends;
  @GwtTransient
  @Column(name = "fiends_of")
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.friend", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<FriendEntity> friendOf;

  @GwtTransient
  @OneToMany(mappedBy = "receiver")
  private Set<PlayerMessageEntity> receivedMessages;
  @GwtTransient
  @OneToMany(mappedBy = "sender")
  private Set<PlayerMessageEntity> sentMessages;

  @Column(name = "logged_in")
  private boolean loggedIn;
  private boolean playing;
  private boolean online;
  @Column(name = "register_date")
  private Date registerDate;
  @Column(name = "last_visited")
  private Date lastVisited;
  @Column(name = "visit_counter")
  private int visitCounter;
  @Column(name = "hash_id")
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

    ShashistEntity that = (ShashistEntity) o;

    if (!sessionId.equals(that.sessionId)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    return sessionId.hashCode();
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
  public Set<PlayerMessageEntity> getReceivedMessages() {
    return receivedMessages;
  }

  @Override
  public void setReceivedMessages(Set<PlayerMessageEntity> playerMessageEntities) {
    this.receivedMessages = playerMessageEntities;
  }

  @Override
  public Set<PlayerMessageEntity> getSentMessages() {
    return sentMessages;
  }

  @Override
  public void setSentMessages(Set<PlayerMessageEntity> playerMessageEntities) {
    this.sentMessages = playerMessageEntities;
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
    if (getPlayerName() == null) {
      String fullName = getFullName().trim();
      if (fullName.isEmpty()) {
        return getEmail().split("@")[0];
      }
      return fullName;
    }
    return getPlayerName();
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
