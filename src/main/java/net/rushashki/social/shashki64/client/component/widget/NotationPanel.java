package net.rushashki.social.shashki64.client.component.widget;


import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.social.shashki64.client.config.ShashkiGinjector;
import net.rushashki.social.shashki64.client.event.ClearNotationEvent;
import net.rushashki.social.shashki64.client.event.ClearNotationEventHandler;
import net.rushashki.social.shashki64.client.event.NotationMoveEvent;
import net.rushashki.social.shashki64.client.event.NotationMoveEventHandler;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 13.03.14
 * Time: 19:32
 */
public class NotationPanel extends ScrollPanel {
  public static final String NOTATION_SEP = "<br>";
  private static final String BITE_SEP = ":";
  private static final String MOVE_SEP = " ";
  private static final String DIV_GARBAGE = "<div[\\s\\w\\d\";:=]*></div>";

  private final EventBus eventBus;
  private int stepCounter;
  private final ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  private static String notation;
  private int cancelCounter;
  private boolean cancelBite;

  public NotationPanel() {
    stepCounter = 1;
    this.eventBus = shashkiGinjector.getEventBus();

    // TODO: Not Compile
    eventBus.addHandler(NotationMoveEvent.TYPE, new NotationMoveEventHandler() {
      @Override
      public void onNotationMove(NotationMoveEvent event) {
        NotationPanel.this.appendMove(event.getMove());
      }
    });
    eventBus.addHandler(ClearNotationEvent.TYPE, new ClearNotationEventHandler() {
      @Override
      public void onClearNotation(ClearNotationEvent event) {
        stepCounter = 1;
        notation = "";
        getElement().setInnerHTML("");
      }
    });

    Scheduler.get().scheduleFinally(() -> {
      setWidth("100px");
    });
  }

  public void appendMove(String move) {
    String html = getElement().getInnerHTML();
    if (move.contains(BITE_SEP)) {
      String firstStep = move.split(BITE_SEP)[0];
      String[] steps = html.split(NOTATION_SEP);
      String lastStroke = steps[steps.length - 1];
      if (lastStroke.endsWith(firstStep)) {
        String lastBeatenStroke = move.split(BITE_SEP)[1];
        if (html.endsWith(NOTATION_SEP)) {
          html = html.substring(0, html.length() - 1) + BITE_SEP + lastBeatenStroke;
        } else {
          html += BITE_SEP + lastBeatenStroke;
        }
        getElement().setInnerHTML(html);
        return;
      }
    }
    if (move.contains(NOTATION_SEP)) {
      html += MOVE_SEP + move; //+ "sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>";
      stepCounter++;
      cancelCounter = 0;
    } else if (move.contains(BITE_SEP) && cancelBite) {
      html += BITE_SEP + move;
      cancelBite = false;
    } else {
      html += stepCounter + ". " + move;
    }
    getElement().setInnerHTML(html);
    notation = html;
    pushScroll();
  }


  // код для прокрутки TextArea в конец
  public void pushScroll() {
    scrollToBottom();
  }

  public static String getNotation() {
    return notation;
  }

  public void cancelMove() {
    notation = notation.replaceAll(DIV_GARBAGE, "");
    String[] notationArray = notation.split(NOTATION_SEP);
    String lastMove = notationArray[notationArray.length - 1];
    String[] lastMoveArray = lastMove.split(" ");
    if (lastMove.contains(BITE_SEP) && !lastMove.contains(MOVE_SEP)) {
      GWT.log(notation + " - " + notation.lastIndexOf(BITE_SEP));
      notation = notation.substring(0, notation.lastIndexOf(BITE_SEP));
      cancelBite = true;
    } else {
      if (lastMoveArray.length == 3) {
        notation = notation.substring(0, notation.lastIndexOf(MOVE_SEP));
      } else {
        notation = notation.substring(0, notation.lastIndexOf(NOTATION_SEP)) + NOTATION_SEP;
        stepCounter -= cancelCounter;
      }
      cancelCounter++;
    }
    getElement().setInnerHTML(notation);
  }
}
