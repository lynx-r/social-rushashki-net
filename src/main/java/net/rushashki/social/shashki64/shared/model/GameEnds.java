package net.rushashki.social.shashki64.shared.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 10.01.15
 * Time: 22:51
 */
public enum GameEnds implements IsSerializable {
  BLACK_WON,
  WHITE_WON,
  BLACK_LEFT,
  WHITE_LEFT,
  SURRENDER_BLACK,
  SURRENDER_WHITE,
  DRAW
}
