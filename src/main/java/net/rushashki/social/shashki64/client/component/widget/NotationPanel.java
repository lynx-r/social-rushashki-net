package net.rushashki.social.shashki64.client.component.widget;


import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.social.shashki64.client.config.ShashkiGinjector;
import net.rushashki.social.shashki64.client.event.OnNotationMoveEvent;
import net.rushashki.social.shashki64.client.event.OnNotationMoveEventHandler;

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
  public static String NOTATION_SEPARATOR = "<br/>";

  public NotationPanel() {
    stepCounter = 1;
    this.eventBus = shashkiGinjector.getEventBus();

    // TODO: Not Compile
    eventBus.addHandler(OnNotationMoveEvent.TYPE, new OnNotationMoveEventHandler() {
      @Override
      public void onNotationStroke(OnNotationMoveEvent event) {
        NotationPanel.this.appendMove(event.getMove());
      }
    });
    Scheduler.get().scheduleFinally(() -> {
      setWidth("100px");
    });
  }

  public void appendMove(String move) {
    String html = getElement().getInnerHTML();
    if (move.contains(":")) {
      String firstStep = move.split(":")[0];
      String[] steps = html.split(NOTATION_SEPARATOR);
      String lastStroke = steps[steps.length - 1];
      if (lastStroke.endsWith(firstStep)) {
        String lastBeatenStroke = move.split(":")[1];
        if (html.endsWith(NOTATION_SEPARATOR)) {
          html = html.substring(0, html.length() - 1) + ":" + lastBeatenStroke;
        } else {
          html += ":" + lastBeatenStroke;
        }
        getElement().setInnerHTML(html);
        return;
      }
    }
    if (move.contains(NOTATION_SEPARATOR)) {
      html += " " + move + "sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>";
      stepCounter++;
    } else {
      html += stepCounter + ". " + move;
    }
    getElement().setInnerHTML(html);
    pushScroll();
  }


  // код для прокрутки TextArea в конец
  public void pushScroll() {
    scrollToBottom();
  }

}
