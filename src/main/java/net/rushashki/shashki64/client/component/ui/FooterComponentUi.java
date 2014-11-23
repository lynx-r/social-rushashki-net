package net.rushashki.shashki64.client.component.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 23.11.14
 * Time: 18:20
 */
public class FooterComponentUi extends Composite {
  interface FooterComponentImplUiBinder extends UiBinder<HTMLPanel, FooterComponentUi> {
  }

  private static FooterComponentImplUiBinder ourUiBinder = GWT.create(FooterComponentImplUiBinder.class);

  public FooterComponentUi() {
    initWidget(ourUiBinder.createAndBindUi(this));
  }
}