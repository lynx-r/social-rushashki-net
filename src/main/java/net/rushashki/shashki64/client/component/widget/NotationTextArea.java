package net.rushashki.shashki64.client.component.widget;


import com.google.gwt.user.client.Window;
import com.google.web.bindery.event.shared.EventBus;
import net.rushashki.shashki64.client.config.ShashkiGinjector;
import net.rushashki.shashki64.client.event.NotationStrokeEvent;
import org.gwtbootstrap3.client.ui.TextArea;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 13.03.14
 * Time: 19:32
 */
public class NotationTextArea extends TextArea {
    private final EventBus eventBus;
    private int stepCounter;
    private final ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;

    public NotationTextArea() {
        stepCounter = 1;
        this.eventBus = shashkiGinjector.getEventBus();

        eventBus.addHandler(NotationStrokeEvent.TYPE, event -> {
           this.appendStroke(event.getStroke());
        });

        this.setReadOnly(true);
    }

    public void appendStroke(String stroke) {
        String text = this.getText();
        if (stroke.contains(":")) {
            String firstStep = stroke.split(":")[0];
            String[] steps = text.split("\n");
            String lastStroke = steps[steps.length - 1];
            if (lastStroke.endsWith(firstStep)) {
                String lastBeatenStroke = stroke.split(":")[1];
                if (text.endsWith("\n")) {
                    text = text.substring(0, text.length() - 1) + ":" + lastBeatenStroke;
                } else {
                    text += ":" + lastBeatenStroke;
                }
                this.setText(text);
                return;
            }
        }
        if (stroke.contains("\n")) {
            text += " " + stroke;
            stepCounter++;
        } else {
            text += stepCounter + ". " + stroke;
        }
        this.setText(text);
        pushScroll();
    }


    // код для прокрутки TextArea в конец
    public void pushScroll() {
        if (Window.Navigator.getUserAgent().toLowerCase().contains("msie")) {
            this.setCursorPos(this.getText().length());
        } else if (Window.Navigator.getUserAgent().toLowerCase().contains("gecko")) {
            scrollToBottom();
        }
    }

    public void scrollToBottom() {
        this.getElement().setScrollTop(this.getElement().getScrollHeight() - this.getElement().getClientHeight());
    }
}
