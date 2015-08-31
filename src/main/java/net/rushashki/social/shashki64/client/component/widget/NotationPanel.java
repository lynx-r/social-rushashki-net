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
  private static final String NOTATION_SEP = "<br>";
  private static final String BITE_SEP = ":";
  private static final String MOVE_SEP = " ";
  private static final String DIV_GARBAGE = "<div[\\s\\w\\d\";:=]*></div>";
  private static final String COUNT_SEP_REGEX = "\\. ";
  private static final String COUNT_SEP = ". ";

  private final EventBus eventBus;
  private int stepCounter;
  private final ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  private static String notation;
  private boolean cancelBite;
  private int cancelCounter;

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
    notation = getElement().getInnerHTML();
    notation = notation.replaceAll(DIV_GARBAGE, "");

    String[] steps = notation.split(NOTATION_SEP);
    if (move.contains(BITE_SEP)) { // была побита шашка
      GWT.log("MOVE" + move);
      // первый шаг. например, h4:f6:d4 - h4
      String firstStep = move.split(BITE_SEP)[0];
      GWT.log("FIRST STEP" + firstStep);
      // шаги из нотации
      // последний шаг. например, 2. f2-g3 f6-g5
      String lastStroke = steps[steps.length - 1];
      GWT.log("LAST STEP" + lastStroke);
      if (lastStroke.endsWith(firstStep)) {
        String lastBeatenStroke = move.split(BITE_SEP)[1];
        GWT.log("LAST BEATEN STROKE" + lastBeatenStroke);
        if (lastStroke.endsWith(NOTATION_SEP)) {
          notation = notation.substring(0, notation.length() - 1) + BITE_SEP + lastBeatenStroke;
        } else {
          notation += BITE_SEP + lastBeatenStroke;
        }
      } else if (lastStroke.contains(firstStep)) {
        GWT.log("Last Stroke" + lastStroke);
        GWT.log("First Step" + firstStep);
      }
    } else { // обычный ход
      String lastMove = steps[steps.length - 1];
      GWT.log("LAST MOVE SIMPLE " + lastMove);
//      if (steps.length == 1) { // первый ход
//        if (lastMove.length() != 0 && !notation.endsWith(NOTATION_SEP)) {
//          notation += MOVE_SEP + move + NOTATION_SEP;
//          stepCounter++;
//        } else {
//          notation += stepCounter + COUNT_SEP + move;
//        }
      if (lastMove.contains(COUNT_SEP) && !notation.endsWith(NOTATION_SEP)) {
        notation += MOVE_SEP + move + NOTATION_SEP;
        stepCounter++;
        GWT.log("1");
      } else if (move.contains(BITE_SEP) && cancelBite) {
        notation += BITE_SEP + move + NOTATION_SEP;
        GWT.log("2");
      } else {
        notation += stepCounter + COUNT_SEP + move;
        GWT.log("3");
      }
      cancelBite = false;
      cancelCounter = 0;
    }
    getElement().setInnerHTML(notation);
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
    // ход. 2. h4:f6 e7:g5
    String move = notationArray[notationArray.length - 1];
    GWT.log("MOVE " + move);
    String lastMove = move.split(COUNT_SEP_REGEX)[1];
    String[] lastMoveArray = lastMove.split(MOVE_SEP);
    GWT.log("LAST MOVE " + lastMove);
    if (lastMove.contains(BITE_SEP) && !lastMove.contains(MOVE_SEP)) { // первый ход
      GWT.log(notation + " - " + notation.lastIndexOf(BITE_SEP));
      if (lastMove.split(BITE_SEP).length == 2) { // первый ход содержит побитые шашки и она одна
        notation = notation.substring(0, notation.lastIndexOf(NOTATION_SEP)) + NOTATION_SEP;
        stepCounter -= cancelCounter;
        cancelCounter = 1;
      } else { // побито несколько шашек
        notation = notation.substring(0, notation.lastIndexOf(BITE_SEP));
        cancelBite = true;
      }
    } else { // второй ход
      if (lastMoveArray.length == 2) {
        // послед ход e7:g5
        final String lastStep = lastMoveArray[1];
        final String[] lastStepArray = lastStep.split(BITE_SEP);
        GWT.log(lastStep);
        if (lastStep.contains(BITE_SEP) && lastStep.split(BITE_SEP).length != 2) { // последний ход содержит побитые
        // шашки
          notation = notation.substring(0, notation.lastIndexOf(BITE_SEP));
          cancelBite = true;
        } else {
          notation = notation.substring(0, notation.lastIndexOf(MOVE_SEP));
          stepCounter -= cancelCounter;
          cancelCounter = 1;
        }
      } else { // первый ход, где нет побитых шашек
        notation = notation.substring(0, notation.lastIndexOf(NOTATION_SEP)) + NOTATION_SEP;
        stepCounter -= cancelCounter;
        cancelCounter = 1;
      }
    }
    getElement().setInnerHTML(notation);
  }
}
