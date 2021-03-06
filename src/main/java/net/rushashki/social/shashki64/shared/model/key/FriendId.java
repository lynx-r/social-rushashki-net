package net.rushashki.social.shashki64.shared.model.key;

import net.rushashki.social.shashki64.shared.model.entity.ShashistEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 01.12.14
 * Time: 14:19
 */
@Embeddable
public class FriendId implements Serializable {

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private ShashistEntity friend;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private ShashistEntity friendOf;

  public ShashistEntity getFriend() {
    return friend;
  }

  public void setFriend(ShashistEntity friend) {
    this.friend = friend;
  }

  public ShashistEntity getFriendOf() {
    return friendOf;
  }

  public void setFriendOf(ShashistEntity friendOf) {
    this.friendOf = friendOf;
  }

}
