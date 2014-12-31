package net.rushashki.social.shashki64.client.component.widget.dialog;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.gwtbootstrap3.client.ui.Button;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 25.01.14
 * Time: 14:12
 */
public class DialogBox extends BasicDialogBox {
  private ScrollPanel contentScrollPanel;
  private HTML contentHTML;
  private final VerticalPanel panel = new VerticalPanel();

  public DialogBox(String header, String content) {
    setText(header);

    contentScrollPanel = new ScrollPanel();
    contentScrollPanel.setHeight("40px");
    contentScrollPanel.setWidth("400px");

    contentHTML = new HTML();
    contentHTML.setHTML(content);
    contentScrollPanel.add(contentHTML);

    panel.add(contentScrollPanel);

    final Button buttonClose = new Button(constants.close(), new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        hide();
      }
    });
    panel.add(buttonClose);

    panel.setCellHorizontalAlignment(buttonClose, HasAlignment.ALIGN_RIGHT);

    add(panel);

    center();

    getElement().getStyle().setZIndex(1000);
  }

  public void setContent(String content) {
    contentHTML.setHTML(content);
  }

  public void setHeader(String header) {
    setText(header);
  }
}
