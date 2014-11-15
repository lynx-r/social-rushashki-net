package net.rushashki.shashki64.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import net.rushashki.shashki64.share.model.Message;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 14.11.14
 * Time: 0:11
 */
public class Shashki64 implements EntryPoint {
  public void onModuleLoad() {
    Button button = new Button("Click me!");
    button.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        Shashki64Service.App.getInstance().getName("Aleks", new AsyncCallback<String>() {
          @Override
          public void onFailure(Throwable caught) {

          }

          @Override
          public void onSuccess(String result) {
            RootPanel.get("content").add(new Label(result));
          }
        });
      }
    });
    RootPanel.get("content").add(button);

    button = new Button("Check persistence.");
    button.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        ChatService.App.getInstance().getMessage(new Message("Hi from client!"), new AsyncCallback<Message>() {
          @Override
          public void onFailure(Throwable caught) {
            DialogBox dialogBox = new DialogBox();
            dialogBox.setHTML(caught.getMessage());
            dialogBox.show();
          }

          @Override
          public void onSuccess(Message result) {
            RootPanel.get("content").add(new Label(result.getId() + " " + result.getMessage()));
          }
        });
      }
    });
    RootPanel.get("content").add(button);
  }
}
