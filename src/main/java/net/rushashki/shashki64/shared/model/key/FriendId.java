package net.rushashki.shashki64.shared.model.key;

import net.rushashki.shashki64.shared.model.Shashist;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
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
  private Shashist friend;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Shashist friendOf;

  public Shashist getFriend() {
    return friend;
  }

  public void setFriend(Shashist friend) {
    this.friend = friend;
  }

  public Shashist getFriendOf() {
    return friendOf;
  }

  public void setFriendOf(Shashist friendOf) {
    this.friendOf = friendOf;
  }

}
