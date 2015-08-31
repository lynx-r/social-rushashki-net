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
      // первый шаг. например, h4:f6:d4 - h4
      String firstStep = move.split(BITE_SEP)[0];
      GWT.log(firstStep);
      // шаги из нотации
      String[] steps = html.split(NOTATION_SEP);
      // последний шаг. например, 2. f2-g3 f6-g5
      String lastStroke = steps[steps.length - 1];
      GWT.log(lastStroke);
      if (lastStroke.endsWith(firstStep)) {
        String lastBeatenStroke = move.split(BITE_SEP)[1];
        GWT.log(lastBeatenStroke);
        if (html.endsWith(NOTATION_SEP)) {
          html = html.substring(0, html.length() - 1) + BITE_SEP + lastBeatenStroke;
        } else {
          html += BITE_SEP + lastBeatenStroke;
        }
        getElement().setInnerHTML(html);
        notation = html;
        GWT.log(notation);
        return;
      }
    }
    if (move.contains(NOTATION_SEP)) {
      html += MOVE_SEP + move; //+ "sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>sdsdfsdf<br>";
      stepCounter++;
      cancelCounter = 0;
    } else if (move.contains(BITE_SEP) && cancelBite) {
      html += BITE_SEP + move;
    } else {
      html += stepCounter + ". " + move;
    }
    cancelBite = false;
    getElement().setInnerHTML(html);
    notation = html;
    GWT.log("Notation " + notation);
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
    GWT.log(notation);
    notation = notation.replaceAll(DIV_GARBAGE, "");
    String[] notationArray = notation.split(NOTATION_SEP);
    String move = notationArray[notationArray.length - 1];
    String lastMove = move.split(". ")[1];
    String[] lastMoveArray = lastMove.split(" ");
    GWT.log(lastMove);
    if (lastMove.contains(BITE_SEP) && !lastMove.contains(MOVE_SEP)) {
      GWT.log(notation + " - " + notation.lastIndexOf(BITE_SEP));
      if (lastMove.split(BITE_SEP).length == 2) {
        notation = notation.substring(0, notation.lastIndexOf(MOVE_SEP));
      } else {
        notation = notation.substring(0, notation.lastIndexOf(BITE_SEP));
        cancelBite = true;
      }
    } else {
      if (lastMoveArray.length == 2) {
        if (lastMove.split(BITE_SEP).length == 2) {
          notation = notation.substring(0, notation.lastIndexOf(MOVE_SEP));
        } else {
          notation = notation.substring(0, notation.lastIndexOf(BITE_SEP));
          cancelBite = true;
        }
      } else {
        notation = notation.substring(0, notation.lastIndexOf(NOTATION_SEP)) + NOTATION_SEP;
        stepCounter -= cancelCounter;
      }
      cancelCounter = 1;
    }
    getElement().setInnerHTML(notation);
  }
}
