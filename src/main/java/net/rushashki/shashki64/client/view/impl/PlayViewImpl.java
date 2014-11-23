package net.rushashki.shashki64.client.view.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import net.rushashki.shashki64.client.view.PlayView;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 14:58
 */
public class PlayViewImpl extends Composite implements PlayView {

  private static PlayViewImplUiBinder ourUiBinder = GWT.create(PlayViewImplUiBinder.class);

  @UiField
  Label playerName;
  private String name;
  private Presenter presenter;

  public PlayViewImpl() {
    initWidget(ourUiBinder.createAndBindUi(this));
  }

  @Override
  public void setPlayer(String playerName) {
    this.name = playerName;
    this.playerName.setText(playerName);
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  interface PlayViewImplUiBinder extends UiBinder<HTMLPanel, PlayViewImpl> {
  }

}