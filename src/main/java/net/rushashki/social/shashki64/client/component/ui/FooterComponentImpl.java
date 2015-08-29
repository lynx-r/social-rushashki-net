package net.rushashki.social.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import org.gwtbootstrap3.client.ui.PanelFooter;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 18:20
 */
public class FooterComponentImpl extends Composite {
  private static Binder ourUiBinder = GWT.create(Binder.class);

  public FooterComponentImpl() {
    initWidget(ourUiBinder.createAndBindUi(this));
  }

  interface Binder extends UiBinder<PanelFooter, FooterComponentImpl> {
  }
}