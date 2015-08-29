package net.rushashki.social.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import org.gwtbootstrap3.client.ui.Jumbotron;
import org.gwtbootstrap3.client.ui.PageHeader;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 30.11.14
 * Time: 11:45
 */
public class GreetingComponentImpl extends BasicComponent {
  private static Binder ourUiBinder = GWT.create(Binder.class);

  @UiField
  PageHeader greeting;

  public GreetingComponentImpl() {
    initWidget(ourUiBinder.createAndBindUi(this));

    greeting.setText(constants.jumbotronGreeting());
    greeting.setSubText(constants.jumbotronSubGreeting());
  }

  interface Binder extends UiBinder<Jumbotron, GreetingComponentImpl> {
  }
}