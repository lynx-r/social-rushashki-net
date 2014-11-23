package net.rushashki.shashki64.client.view.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.shashki64.client.place.PlayPlace;
import net.rushashki.shashki64.client.view.HomeView;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 12:32
 */
public class HomeViewImpl extends Composite implements HomeView {
  private static HomeViewImplUiBinder ourUiBinder = GWT.create(HomeViewImplUiBinder.class);

  @UiField
  Label greeting;
  @UiField
  Anchor playLink;

  private Presenter presenter;
  private String name;

  @Inject
  public HomeViewImpl(final EventBus eventBus) {
    initWidget(ourUiBinder.createAndBindUi(this));
  }

  @Override
  public void setName(String greetingName) {
    this.name = greetingName;
    greeting.setText(greetingName);
  }

  @UiHandler("playLink")
  void onClickPlay(ClickEvent event) {
    presenter.goTo(new PlayPlace(name));
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  interface HomeViewImplUiBinder extends UiBinder<HTMLPanel, HomeViewImpl> {
  }
}