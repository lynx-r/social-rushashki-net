package net.rushashki.social.shashki64.shashki;


import com.ait.lienzo.client.core.animation.AnimationProperties;
import com.ait.lienzo.client.core.animation.AnimationProperty;
import com.ait.lienzo.client.core.animation.AnimationTweener;
import com.ait.lienzo.client.core.event.NodeMouseDownEvent;
import com.ait.lienzo.client.core.event.NodeMouseDownHandler;
import com.ait.lienzo.client.core.shape.Circle;
import com.ait.lienzo.client.core.shape.Group;
import com.ait.lienzo.client.core.shape.Star;
import com.ait.lienzo.client.core.types.Shadow;
import com.ait.lienzo.shared.core.types.Color;
import com.ait.lienzo.shared.core.types.ColorName;

/**
 * Created with IntelliJ IDEA.
 * Profile: alekspo
 * Date: 07.12.13
 * Time: 21:08
 */
public class Draught extends Group {
    private final int deskSide;
    private static Draught selectedDraught;
    private Board board;

    private int row;
    private int col;
    private boolean white;

    private Circle mainCircle = new Circle(0);
    private Circle innerCircle1 = new Circle(0);
    private Circle innerCircle2 = new Circle(0);
    private Circle innerCircle3 = new Circle(0);
    private Star queenStar = new Star(5, 0, 0);

    private int rows;
    private int cols;

    private boolean queen;

    private Draught currentDraught;

    public Draught(int deskSide, int rows, int cols, int row, int col, boolean white) {
        this.deskSide = deskSide;
        this.row = row;
        this.col = col;
        this.rows = rows;
        this.cols = cols;
        this.white = white;

//    this.setDraggable(true);
        this.setListening(true);

        this.setAlpha(.8);
        this.add(mainCircle);
        this.add(innerCircle1);
        this.add(innerCircle2);
        this.add(innerCircle3);

        queenStar.setFillColor(white ? ColorName.BLUE : ColorName.RED);

        Circle[] circles = {mainCircle, innerCircle1, innerCircle2, innerCircle3};

        int i = 0;
        for (Circle circle : circles) {
            if (i % 2 == 0) {
                circle.setShadow(new Shadow(Color.fromColorString("#000"), 6, 0, 4));
            } else {
                circle.setShadow(new Shadow(Color.fromColorString("#000"), 6, 0, -4));
            }
            i++;
            circle.setFillColor(white ? ColorName.WHITE : Color.fromColorString("#555"));
        }

        updateShape();

        // TODO: Not Compile
        addNodeMouseDownHandler(new NodeMouseDownHandler() {
            @Override
            public void onNodeMouseDown(NodeMouseDownEvent nodeMouseDownEvent) {
                if (!isValidStroke()) {
                    return;
                }

                board = (Board) getParent();
                currentDraught = (Draught) nodeMouseDownEvent.getSource();

                if (selectedDraught != null) {
                    AnimationProperties props = new AnimationProperties();
                    props.push(AnimationProperty.Properties.SCALE(1));

                    selectedDraught.animate(AnimationTweener.LINEAR, props, 100);
                }

                AnimationProperties props = new AnimationProperties();
                props.push(AnimationProperty.Properties.SCALE(1.3));

                currentDraught.animate(AnimationTweener.LINEAR, props, 100);

                selectedDraught = currentDraught;

                board.resetDeskDrawing();
                board.highlightAllowedMoves(selectedDraught);
            }
        });

//    addNodeMouseUpHandler(nodeMouseUpEvent -> {
//      Board board = (Board) getParent();
//      board.getBackgroundLayer().resetDeskDrawing();
//    });

//    addNodeDragEndHandler(new NodeDragEndHandler() {
//      @Override
//      public void onNodeDragEnd(NodeDragEndEvent nodeDragEndEvent) {
//        if (!isValidStroke()) {
//          return;
//        }
//        Board board = (Board) getParent();
//        Square newSquare = board.getBackgroundLayer().getSquare((double) nodeDragEndEvent.getX(),  // - mainWindowController.getSceneOffsetX(),
//            (double) nodeDragEndEvent.getY());
//        if (newSquare != null && newSquare != startSquare
//            && startSquare.isOnLine(newSquare) && isAllowed(newSquare)) {

//          double mouseDeltaX = newSquare.getCenterX() - getX();
//          double mouseDeltaY = newSquare.getCenterY() - getY();

//          animate(AnimationTweener.LINEAR, new AnimationProperties(
//              AnimationProperty.Properties.X(getX() + mouseDeltaX),
//              AnimationProperty.Properties.Y(getY() + mouseDeltaY)
//          ), timeReturnToStartPos);
//          String captured = board.move(startSquare, newSquare);

//          boolean simpleStroke = "null".equals(captured);
//          if ("null".equals(captured) || "none".equals(captured.split(",")[2])) {
//            board.toggleTurn();
//          }
//          if (!queen) {
//            if (Draught.this.getRow() == 0) {
//              setQueen(true);
//            }
//          }

//          String op = simpleStroke ? "-" : ":";
//          NotationTextArea.get().appendStroke(
//              startSquare.toNotation(isWhite(), false, false)
//              + op
//              + newSquare.toNotation(isWhite(), true, false)
//              + (isWhite() ? "" : "\n")
//          );
//          ChatUtil.sendStep(clientFactory.getChatChannel(), String.valueOf(clientFactory.getCurrentGame().getId()),
//              clientFactory.getProfile().getUserId(), clientFactory.getOpponentId(),
//              startSquare.toSend(), newSquare.toSend(), captured);
//        } else { // translate circle on initial position when we try to move it on incorrect place
//          double mouseDeltaX = mouseStartX - nodeDragEndEvent.getX(); // + mainWindowController.getSceneOffsetX();
//          double mouseDeltaY = mouseStartY - nodeDragEndEvent.getY(); // + mainWindowController.getSceneOffsetY();

//          animate(AnimationTweener.LINEAR, new AnimationProperties(
//              AnimationProperty.Properties.X(getX() + mouseDeltaX),
//              AnimationProperty.Properties.Y(getY() + mouseDeltaY)
//          ), timeReturnToStartPos);
//        }
//        board.getBackgroundLayer().resetDeskDrawing();
//      }
//    });
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public void updateShape() {
        double x = col * deskSide / rows;
        double y = row * deskSide / cols;
        double squareSize = deskSide / rows;
        this.setX(x + squareSize / 2 + 20);
        this.setY(y + squareSize / 2);

        double radius = squareSize / 2 - 5;

        mainCircle.setRadius(radius);
        innerCircle1.setRadius(radius - radius / 4);
        if (isQueen()) {
            this.remove(innerCircle2);
            this.remove(innerCircle3);

            this.add(queenStar);
            queenStar.setInnerRadius(radius - radius / 1.3);
            queenStar.setOuterRadius(radius - radius / 2);
        } else {
            if (!getChildNodes().contains(innerCircle2)) {
                this.add(innerCircle2);
            }
            if (!getChildNodes().contains(innerCircle3)) {
                this.add(innerCircle3);
            }

            if (this.getChildNodes().contains(queenStar)) {
                this.remove(queenStar);
            }

            innerCircle2.setRadius(radius - radius / 2);
            innerCircle3.setRadius(radius - radius / 1.3);
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setQueen(boolean queen) {
        this.queen = queen;
        updateShape();
    }

    public boolean isQueen() {
        return queen;
    }

    private boolean isValidStroke() {
        Board board = (Board) getParent();
        return board.myTurn() && isWhite() == board.isWhite() && !board.isEmulate();
    }

    private boolean isAllowed(Square newSquare) {
        return newSquare.getAlpha() < 1.0;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public static Draught getSelectedDraught() {
        return selectedDraught;
    }
}
