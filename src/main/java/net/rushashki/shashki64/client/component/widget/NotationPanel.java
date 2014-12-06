package net.rushashki.shashki64.client.component.widget;


import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.client.event.OnNotationStrokeEvent;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 13.03.14
 * Time: 19:32
 */
public class NotationPanel extends ScrollPanel {
  private final EventBus eventBus;
  private int stepCounter;
  private final ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;

  public NotationPanel() {
    stepCounter = 1;
    this.eventBus = shashkiGinjector.getEventBus();

    eventBus.addHandler(OnNotationStrokeEvent.TYPE, event -> {
      this.appendStroke(event.getStroke());
    });

    Scheduler.get().scheduleFinally(() -> {
      this.setWidth("80px");
    });
  }

  public void appendStroke(String stroke) {
    Window.alert(stroke);
    String text = this.getElement().getInnerText();
    if (stroke.contains(":")) {
      String firstStep = stroke.split(":")[0];
      String[] steps = text.split("<br />");
      String lastStroke = steps[steps.length - 1];
      if (lastStroke.endsWith(firstStep)) {
        String lastBeatenStroke = stroke.split(":")[1];
        if (text.endsWith("<br />")) {
          text = text.substring(0, text.length() - 1) + ":" + lastBeatenStroke;
        } else {
          text += ":" + lastBeatenStroke;
        }
        this.getElement().setInnerHTML(text);
        return;
      }
    }
    if (stroke.contains("<br />")) {
      text += " " + stroke;
      stepCounter++;
    } else {
      text += stepCounter + ". " + stroke;
    }
    this.getElement().setInnerHTML(text);
    pushScroll();
  }


  // код для прокрутки TextArea в конец
  public void pushScroll() {
    this.scrollToBottom();
  }

}
