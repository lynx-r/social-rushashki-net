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
public class FooterComponentUi extends Composite {
  private static FooterComponentImplUiBinder ourUiBinder = GWT.create(FooterComponentImplUiBinder.class);

  public FooterComponentUi() {
    initWidget(ourUiBinder.createAndBindUi(this));
  }

  interface FooterComponentImplUiBinder extends UiBinder<PanelFooter, FooterComponentUi> {
  }
}