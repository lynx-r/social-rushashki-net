package net.rushashki.social.shashki64.shashki;

import com.ait.lienzo.client.core.animation.*;
import com.ait.lienzo.client.core.event.NodeMouseClickEvent;
import com.ait.lienzo.client.core.event.NodeMouseClickHandler;
import com.ait.lienzo.client.core.event.NodeTouchEndEvent;
import com.ait.lienzo.client.core.event.NodeTouchEndHandler;
import com.ait.lienzo.client.core.shape.Layer;
import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import net.rushashki.social.shashki64.client.config.ShashkiGinjector;
import net.rushashki.social.shashki64.client.event.*;
import net.rushashki.social.shashki64.shashki.dto.MoveDto;
import net.rushashki.social.shashki64.shashki.util.Operator;
import net.rushashki.social.shashki64.shashki.util.PossibleOperators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * Profile: alekspo
 * Date: 08.12.13
 * Time: 13:26
 */
public class Board extends Layer {
  private final BoardBackgroundLayer backgroundLayer;
  private List<Square> capturedSquares = new ArrayList<>();
  private List<Draught> myDraughtList;
  private List<Draught> opponentDraughtList;

  private boolean white;
  private boolean turn;
  private int rows;
  private int cols;
  private final double removeDraughtFade = 400;
  private final double moveDraughtDuration = 800;
  private boolean emulate = false; // эмулировать шашки
  private HashMap<String, Integer> alphMap;
  // стек ходов шашек, когда они становятся дамками
  private Stack<Integer> queenStepStack = new Stack<>();

  private ShashkiGinjector shashkiGinjector = ShashkiGinjector.INSTANCE;
  private EventBus eventBus;
  private List<Square> highlightedSquares = new ArrayList<>();
  //  private String lastEndMove;
//  private String lastStartMove;
//  private String lastCaptured;
  private HandlerRegistration playMoveOpponentHR;

  private Stack<MoveDto> moveStack = new Stack<>();

  public Board(BoardBackgroundLayer backgroundLayer, int rows, int cols, boolean white) {

    this.backgroundLayer = backgroundLayer;
    this.white = white;
    this.eventBus = shashkiGinjector.getEventBus();
    turn = white;

    this.rows = rows;
    this.cols = cols;

    myDraughtList = new ArrayList<>(rows / 2 * 3);
    opponentDraughtList = new ArrayList<>(rows / 2 * 3);

    placeDraughts();

    alphMap = new HashMap<>();
    alphMap.put("a", 0);
    alphMap.put("b", 1);
    alphMap.put("c", 2);
    alphMap.put("d", 3);
    alphMap.put("e", 4);
    alphMap.put("f", 5);
    alphMap.put("g", 6);
    alphMap.put("h", 7);

    handlers();
  }

  private void handlers() {
    eventBus.addHandler(PlayMoveEvent.TYPE, new PlayMoveEventHandler() {
      @Override
      public void onPlayMove(PlayMoveEvent event) {
        final MoveDto move = event.getMove();
        if (move.isCancel()) {
          eventBus.fireEvent(new NotationCancelMoveEvent(move));
          if (isMyTurn()) {
            moveOpponent(move);
          } else {
            moveCanceled(move);
          }
        }
      }
    });

    addNodeMouseClickHandler(new NodeMouseClickHandler() {
      @Override
      public void onNodeMouseClick(NodeMouseClickEvent nodeMouseClickEvent) {
        Board.this.moveDraught(nodeMouseClickEvent.getX(), nodeMouseClickEvent.getY());
      }
    });

    addNodeTouchEndHandler(new NodeTouchEndHandler() {
      @Override
      public void onNodeTouchEnd(NodeTouchEndEvent nodeTouchEndEvent) {
        Board.this.moveDraught(nodeTouchEndEvent.getX(), nodeTouchEndEvent.getY());
      }
    });

    playMoveOpponentHR = eventBus.addHandler(PlayMoveOpponentEvent.TYPE, new PlayMoveOpponentEventHandler() {
      @Override
      public void onPlayMoveOpponent(PlayMoveOpponentEvent event) {
        Board.this.moveOpponent(event.getMove());
      }
    });

    eventBus.addHandler(RemovePlayMoveOpponentHandlerEvent.TYPE, new RemoveWebsocketHandlersEventHandler() {
      @Override
      public void onRemovePlayMoveOpponentHandler(RemovePlayMoveOpponentHandlerEvent event) {
        playMoveOpponentHR.removeHandler();
      }
    });
  }

  private void placeDraughts() {
    for (int row = 0; row < 3; row++) {
//    for(int row = 3; row < 4; row++) {
      for (int col = 0; col < 8; col++) {
//      for (int col = 2; col < 3; col++) {
        if (Square.isValid(row, col)) {
          opponentDraughtList.add(addDraught(row, col, !white));
        }
      }
    }

    // Now establish the Black side
    for (int row = 5; row < 8; row++) {
//    for(int row = 4; row < 5; row++) {
      for (int col = 0; col < 8; col++) {
//      for (int col = 5; col < 6; col++) {
        if (Square.isValid(row, col)) {
          myDraughtList.add(addDraught(row, col, white));
        }
      }
    }

/*
    if (isWhite()) {
      opponentDraughtList.add(addDraught(3, 4, !white));
    } else {
      myDraughtList.add(addDraught(4, 3, white));
    }

    if (isWhite()) {
      myDraughtList.add(addDraught(4, 3, white));
    } else {
      opponentDraughtList.add(addDraught(3, 4, !white));
    }
*/
  }

  private Draught addDraught(int row, int col, boolean white) {
    try {
      Square square = backgroundLayer.getSquare(row, col);
      if (Square.isValid(row, col)) {
        Draught draught = new Draught(backgroundLayer.getDeskSide(), rows, cols, row, col, white, backgroundLayer.getOffsetX());
        add(draught);
        square.setOccupant(draught);
        return draught;
      }
    } catch (SquareNotFoundException e) {
      GWT.log(e.getLocalizedMessage(), e);
    }
    return null;
  }

  /**
   * Функция заполняет массив highlightedSquares
   *
   * @param clickedPiece
   */
  public void highlightAllowedMoves(Draught clickedPiece) {
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        try {
          Square square = backgroundLayer.getSquare(row, col);
          Draught draught = square.getOccupant();
          if (draught != null && draught.isWhite() == clickedPiece.isWhite()) {
            highlightPossibleMoves(draught, clickedPiece);
          }
        } catch (SquareNotFoundException ignore) {
//          GWT.log(e.getLocalizedMessage(), e);
        }
      }
    }
    // если нашли шашку, которая будет побита, удаляем прорисоку обычных ходов
    if (!capturedSquares.isEmpty()) {
      for (int row = 0; row < rows; row++) {
        for (int col = 0; col < cols; col++) {
          try {
            Square square = backgroundLayer.getSquare(row, col);
            if (null != square && isHighlightedSquare(square)) {
              fadeOutSquare(square);
              highlightedSquares.remove(square);
            }
          } catch (SquareNotFoundException e) {
            GWT.log(e.getLocalizedMessage(), e);
          }
        }
      }
    }
    backgroundLayer.draw();
  }

  /**
   * Find all possible Squares to which this Draught can move
   *
   * @param p Draught for which moves should be found
   * @return A List of Squares to which this Draught can move
   */
  private void highlightPossibleMoves(Draught p, Draught clickedDraught) {
    /* Possible moves include up-left, up-right, down-left, down-right
     * This corresponds to (row-- col--), (row-- col++),
		 * 						(row++ col--), (row++ col++) respectively
		 */
    List<Square> possibleMoves = new ArrayList<>();
    List<Square> jumpMoves = new ArrayList<>();
    boolean white = p.isWhite();
    int row = p.getRow();
    int col = p.getCol();

    //Begin checking which moves are possible, keeping in mind that only black shashki may move up
    //and only red shashki may move downwards

    boolean queen = p.isQueen();
    if (this.white) {
      possibleMovePair(Operator.SUBTRACTION, Operator.SUBTRACTION, row, col, white, white, queen,
          possibleMoves, jumpMoves, true);
      possibleMovePair(Operator.SUBTRACTION, Operator.ADDITION, row, col, white, white, queen,
          possibleMoves, jumpMoves, true);
      possibleMovePair(Operator.ADDITION, Operator.SUBTRACTION, row, col, !white, white, queen,
          possibleMoves, jumpMoves, true);
      possibleMovePair(Operator.ADDITION, Operator.ADDITION, row, col, !white, white, queen,
          possibleMoves, jumpMoves, true);
    } else {
      // top-left
      possibleMovePair(Operator.SUBTRACTION, Operator.SUBTRACTION, row, col, !white, white, queen,
          possibleMoves, jumpMoves, true);
      // top-right
      possibleMovePair(Operator.SUBTRACTION, Operator.ADDITION, row, col, !white, white, queen,
          possibleMoves, jumpMoves, true);
      // bottom-left
      possibleMovePair(Operator.ADDITION, Operator.SUBTRACTION, row, col, white, white, queen,
          possibleMoves, jumpMoves, true);
      // bottom-right
      possibleMovePair(Operator.ADDITION, Operator.ADDITION, row, col, white, white, queen,
          possibleMoves, jumpMoves, true);
    }

    if (p == clickedDraught) {
      if (!jumpMoves.isEmpty()) {
        for (Square currentSq : jumpMoves) {
          try {
            Square initSq = backgroundLayer.getSquare(clickedDraught.getRow(), clickedDraught.getCol());
            if (initSq.isOnLine(currentSq)) {
              highlightSquareToBeat(currentSq);
              highlightedSquares.add(currentSq);
            }
          } catch (SquareNotFoundException e) {
            GWT.log(e.getLocalizedMessage(), e);
          }
        }
      } else if (!possibleMoves.isEmpty()) {
        for (Square square : possibleMoves) {
          fadeInSquare(square);
          highlightedSquares.add(square);
        }
      }
    }
  }

  private void fadeInSquare(Square square) {
    square.setAlpha(.5);
  }

  private void fadeOutSquare(Square square) {
    square.setAlpha(1.0);
  }

  private boolean isHighlightedSquare(Square square) {
    return .5 == square.getAlpha();
  }

  private void highlightSquareToBeat(Square square) {
    square.setAlpha(.4);
  }

  /**
   * Possible move in next and back direction
   *
   * @param opRow
   * @param opCol
   * @param row
   * @param col
   * @param white
   * @param sideWhite        - цвет текущей шашки
   * @param outPossibleMoves
   * @param outJumpMoves
   */
  private void possibleMovePair(Operator opRow, Operator opCol, int row, int col, boolean white, boolean sideWhite,
                                boolean queen, List<Square> outPossibleMoves, List<Square> outJumpMoves,
                                boolean straightQueen) {
    if (inBounds(opRow.apply(row, 1), opCol.apply(col, 1)) && (white || queen)) {
      Square square = null, nextSquare = null;
      try {
        square = backgroundLayer.getSquare(opRow.apply(row, 1), opCol.apply(col, 1));
        nextSquare = backgroundLayer.getSquare(opRow.apply(row, 2), opCol.apply(col, 2));
      } catch (SquareNotFoundException ignore) {
//        GWT.log(e.getLocalizedMessage(), e);
      }
      // Check moves to the op1, op2 of this Draught
      if (square != null && !square.isOccupied()) {
        outPossibleMoves.add(square);
        if (queen) {
          possibleMovePair(opRow, opCol, opRow.apply(row, 1), opCol.apply(col, 1), white, sideWhite,
              true, outPossibleMoves, outJumpMoves, straightQueen);
        }
      } else {
        //if square is occupied, and the color of the Draught in square is
        //not equal to the Draught whose moves we are checking, then
        //check to see if we can make the jump by checking
        //the next square in the same direction
        if (queen) {
          if (inBounds(opRow.apply(row, 2), opCol.apply(col, 2))) {
            if (nextSquare != null && !nextSquare.isOccupied()
                && square != null && square.getOccupant().isWhite() != sideWhite) {
              int i = opRow.apply(row, 2);
              int j = opCol.apply(col, 2);
              List<Square> jumps = new ArrayList<>();
              List<Square> oneJumps = new ArrayList<>();
              Square startSquareLoop;
              try {
                startSquareLoop = backgroundLayer.getSquare(i, j);
              } catch (SquareNotFoundException e) {
                GWT.log(e.getLocalizedMessage(), e);
                return;
              }
              while (inBounds(i, j) && !startSquareLoop.isOccupied()) {
                final Square loopSquare;
                try {
                  loopSquare = backgroundLayer.getSquare(i, j);
                } catch (SquareNotFoundException e) {
                  GWT.log(e.getLocalizedMessage(), e);
                  continue;
                }
                if (straightQueen) {
                  if (opRow.equals(PossibleOperators.SUB) && opCol.equals(PossibleOperators.SUB)
                      || opRow.equals(PossibleOperators.ADD) && opCol.equals(PossibleOperators.ADD)) { // top-left & bottom-right
                    possibleMovePair(Operator.ADDITION, Operator.SUBTRACTION, i, j, white, sideWhite, true,
                        outPossibleMoves, outJumpMoves, false);
                    possibleMovePair(Operator.SUBTRACTION, Operator.ADDITION, i, j, white, sideWhite, true,
                        outPossibleMoves, outJumpMoves, false);
                  } else if (opRow.equals(PossibleOperators.SUB) && opCol.equals(PossibleOperators.ADD)
                      || opRow.equals(PossibleOperators.ADD) && opCol.equals(PossibleOperators.SUB)) { // bottom-left & top-right
                    possibleMovePair(Operator.SUBTRACTION, Operator.SUBTRACTION, i, j, white, sideWhite, true,
                        outPossibleMoves, outJumpMoves, false);
                    possibleMovePair(Operator.ADDITION, Operator.ADDITION, i, j, white, sideWhite, true,
                        outPossibleMoves, outJumpMoves, false);
                  }
                  if (!outJumpMoves.isEmpty()) {
                    jumps.add(loopSquare);
                    outJumpMoves.clear();
                  } else if (jumps.isEmpty()) {
                    oneJumps.add(loopSquare);
                  }
                } else {
                  outJumpMoves.add(loopSquare);
                }
                i = opRow.apply(i, 1);
                j = opCol.apply(j, 1);
              }
              if (!jumps.isEmpty()) {
                outJumpMoves.addAll(jumps);
              } else if (!oneJumps.isEmpty()) {
                outJumpMoves.addAll(oneJumps);
              }
              if (!capturedSquares.contains(square)) {
                capturedSquares.add(square);
              }
            }
          }
        } else {
          if (inBounds(opRow.apply(row, 2), opCol.apply(col, 2))) {
            if (nextSquare != null && !nextSquare.isOccupied()
                && square != null && square.getOccupant().isWhite() != sideWhite) {
              outJumpMoves.add(nextSquare);
              if (!capturedSquares.contains(square)) {
                capturedSquares.add(square);
              }
            }
          }
        }
      }
    }

    if (!queen) {
      if (inBounds(opRow.apply(row, 2), opCol.apply(col, 2))) {
        Square square = null, nextSquare = null;
        try {
          square = backgroundLayer.getSquare(opRow.apply(row, 1), opCol.apply(col, 1));
          nextSquare = backgroundLayer.getSquare(opRow.apply(row, 2), opCol.apply(col, 2));
        } catch (SquareNotFoundException e) {
          GWT.log(e.getLocalizedMessage(), e);
        }
        if (nextSquare != null && !nextSquare.isOccupied() && square != null && square.isOccupied()
            && square.getOccupant().isWhite() != sideWhite) {
          outJumpMoves.add(nextSquare);
          if (!capturedSquares.contains(square)) {
            capturedSquares.add(square);
          }
        }
      }
    }
  }

  public void resetDesk() {
    backgroundLayer.resetDeskDrawing();
    highlightedSquares.clear();
  }

  public Square getSquare(double row, double col) throws SquareNotFoundException {
    return backgroundLayer.getSquare(row, col);
  }

  public Square getSquare(int row, int col) throws SquareNotFoundException {
    return backgroundLayer.getSquare(row, col);
  }

  private void possibleNextMovePair(Square takenSquare, Operator opRow, Operator opCol, int row, int col,
                                    boolean queen, List<Square> outJumpMoves) {
    if (inBounds(opRow.apply(row, 1), opCol.apply(col, 1))) {
      // Check moves to the op1, op2 of this Draught
      Square jumpSquare;
      try {
        jumpSquare = backgroundLayer.getSquare(opRow.apply(row, 2), opCol.apply(col, 2));
      } catch (SquareNotFoundException e) {
        GWT.log(e.getLocalizedMessage(), e);
        return;
      }
      Square capturedSquare;
      try {
        capturedSquare = backgroundLayer.getSquare(opRow.apply(row, 1), opCol.apply(col, 1));
      } catch (SquareNotFoundException e) {
        GWT.log(e.getLocalizedMessage(), e);
        return;
      }
      //if square is occupied, and the color of the Draught in square is
      //not equal to the Draught whose moves we are checking, then
      //check to see if we can make the jump by checking
      //the next square in the same direction
      if (queen) {
        if (inBounds(opRow.apply(row, 2), opCol.apply(col, 2))) {
          final Square square;
          try {
            square = backgroundLayer.getSquare(row, col);
          } catch (SquareNotFoundException e) {
            GWT.log(e.getLocalizedMessage(), e);
            return;
          }
          if (capturedSquare.isBetween(square, takenSquare)) {
            return;
          }
          int r = row;
          int c = col;
          while (inBounds(opRow.apply(r, 2), opCol.apply(c, 2))
              && !capturedSquare.isOccupied()) {
            r = opRow.apply(r, 1);
            c = opCol.apply(c, 1);
            try {
              capturedSquare = backgroundLayer.getSquare(opRow.apply(r, 1), opCol.apply(c, 1));
            } catch (SquareNotFoundException e) {
              GWT.log(e.getLocalizedMessage(), e);
              return;
            }
            try {
              jumpSquare = backgroundLayer.getSquare(opRow.apply(r, 2), opCol.apply(c, 2));
            } catch (SquareNotFoundException e) {
              GWT.log(e.getLocalizedMessage(), e);
              return;
            }
          }
          if (jumpSquare != null && !jumpSquare.isOccupied()
              && capturedSquare.getOccupant().isWhite() != white) {
            int i = jumpSquare.getRow();
            int j = jumpSquare.getCol();
            Square squareStartLoop;
            try {
              squareStartLoop = backgroundLayer.getSquare(i, j);
            } catch (SquareNotFoundException e) {
              GWT.log(e.getLocalizedMessage(), e);
              return;
            }
            while (inBounds(i, j) && !squareStartLoop.isOccupied()) {
              final Square squareLoop;
              try {
                squareLoop = backgroundLayer.getSquare(i, j);
              } catch (SquareNotFoundException e) {
                GWT.log(e.getLocalizedMessage(), e);
                continue;
              }
              outJumpMoves.add(squareLoop);
              i = opRow.apply(i, 1);
              j = opCol.apply(j, 1);
            }
            if (!capturedSquares.contains(capturedSquare)) {
              capturedSquares.add(capturedSquare);
            }
          }
        }
      } else {
        final Square square;
        try {
          square = backgroundLayer.getSquare(opRow.apply(row, 1), opCol.apply(col, 1));
        } catch (SquareNotFoundException e) {
          GWT.log(e.getLocalizedMessage(), e);
          return;
        }
        if (square.isOccupied()) {
          // нельзя возвращяться назад
          if (takenSquare == jumpSquare) {
            return;
          }
          if (inBounds(opRow.apply(row, 2), opCol.apply(col, 2))) {
            if (!jumpSquare.isOccupied()
                && capturedSquare.getOccupant().isWhite() != white) {
              outJumpMoves.add(jumpSquare);
              if (!capturedSquares.contains(capturedSquare)) {
                capturedSquares.add(capturedSquare);
              }
            }
          }
        }
      }
    }
  }

  /**
   * получаем флаги передвижения и взятую шашку
   *
   * @param from 				The square from which we are moving
   * @param to				The square to which we are moving
   *
   * @return возвращете передвижение с установленными флагами и взятой шашкой
   */
  public MoveDto move(Square from, Square to) {
    MoveDto move = new MoveDto();
    Draught beingMoved = from.getOccupant();

    from.setOccupant(null);
    beingMoved.setPosition(to.getRow(), to.getCol());
    to.setOccupant(beingMoved);

    if (!capturedSquares.isEmpty()) {
      //A jump has been performed, so get the Square that lies between from and to
      Square takenSquare = null;
      for (Square s : capturedSquares) {
        if (to.isOnLine(s) && s.isBetween(from, to)) {
          takenSquare = s;
          break;
        }
      }

      if (takenSquare == null) {
        move.setOnSimpleMove();
        return move;
      }

      int row = to.getRow();
      int col = to.getCol();
      boolean queen = beingMoved.isQueen();
      if (!queen && row == 0) {
        queen = true;
      }
      List<Square> jumpMoves = new ArrayList<>();
      if (this.white) {
        possibleNextMovePair(from, Operator.SUBTRACTION, Operator.SUBTRACTION, row, col, queen,
            jumpMoves);
        possibleNextMovePair(from, Operator.SUBTRACTION, Operator.ADDITION, row, col, queen,
            jumpMoves);
        possibleNextMovePair(from, Operator.ADDITION, Operator.SUBTRACTION, row, col, queen,
            jumpMoves);
        possibleNextMovePair(from, Operator.ADDITION, Operator.ADDITION, row, col, queen,
            jumpMoves);
      } else {
        // top-left
        possibleNextMovePair(from, Operator.SUBTRACTION, Operator.SUBTRACTION, row, col, queen,
            jumpMoves);
        // top-right
        possibleNextMovePair(from, Operator.SUBTRACTION, Operator.ADDITION, row, col, queen,
            jumpMoves);
        // bottom-left
        possibleNextMovePair(from, Operator.ADDITION, Operator.SUBTRACTION, row, col, queen,
            jumpMoves);
        // bottom-right
        possibleNextMovePair(from, Operator.ADDITION, Operator.ADDITION, row, col, queen,
            jumpMoves);
      }

      move.setTakenSquare(takenSquare);
      if (jumpMoves.isEmpty()) {
        move.setOnStopBeat();
      } else {
        move.setOnContinueBeat();
      }

      removeDraughtFrom(takenSquare);

      // если предыдущий ход простой (без взятия), тогда устанавливаем флаг на начало взятия
      if (getLastMove().isSimple()) {
        move.setOnStartBeat();
      }
    } else {
      move.setOnSimpleMove();
    }

    return move;
  }

  public void removeDraughtFrom(Square takenSquare) {
    removeDraughtFrom(takenSquare, false);
  }

  private void removeDraughtFrom(Square takenSquare, boolean clearDesk) {
    opponentDraughtList.remove(takenSquare.getOccupant());
    final Draught takenDraught = takenSquare.getOccupant();
    if (takenDraught == null) {
      return;
    }
    myDraughtList.remove(takenDraught);
    takenSquare.setOccupant(null);
    AnimationProperties props = new AnimationProperties();
    props.push(AnimationProperty.Properties.ALPHA(0));
    takenDraught.animate(AnimationTweener.LINEAR, props,
        removeDraughtFade, new IAnimationCallback() {
          @Override
          public void onStart(IAnimation iAnimation, IAnimationHandle iAnimationHandle) {
          }

          @Override
          public void onFrame(IAnimation iAnimation, IAnimationHandle iAnimationHandle) {
          }

          @Override
          public void onClose(IAnimation iAnimation, IAnimationHandle iAnimationHandle) {
            remove(takenDraught);
            if (!isEmulate() && !clearDesk) {
              eventBus.fireEvent(new CheckWinnerEvent());
            }
          }
        });
    capturedSquares = new ArrayList<>();
  }

  public boolean inBounds(int row, int col) {
    return row >= 0 && row < rows && col >= 0 && col < cols;
  }

  private Square parseStep(String move) throws SquareNotFoundException {
    int sRow = 8 - Integer.parseInt(move.substring(1, 2));
    int sCol = alphMap.get(move.substring(0, 1));

    return backgroundLayer.getSquare(sRow, sCol);
  }

  private Square findCaptured(Square firstStep, Square secondStep) {
    Square captured = null;
    OUTERLOOP:
    for (int n = 0; n < rows; n++) {
      for (int m = 0; m < cols; m++) {
        Square current;
        try {
          current = backgroundLayer.getSquare(n, m);
        } catch (SquareNotFoundException e) {
          GWT.log(e.getLocalizedMessage(), e);
          continue;
        }
        if (null != current && null != current.getOccupant() && current.isBetween(firstStep, secondStep)
            && current.isOnLine(firstStep)) {
          captured = current;
          break OUTERLOOP;
        }
      }
    }
    return captured;
  }

//  public void moveEmulatedNextWhite(String move, int stepCursor) {
//    if (move.contains(ANNOTATION_SIMPLE_MOVE)) {
//      String[] steps = move.split(ANNOTATION_SIMPLE_MOVE);
//      Square startSquare, endSquare;
//      try {
//        startSquare = parseStep(steps[0]);
//        endSquare = parseStep(steps[1]);
//      } catch (SquareNotFoundException e) {
//        GWT.log(e.getLocalizedMessage(), e);
//        return;
//      }
//      move(startSquare, endSquare, null, false, stepCursor);
//    } else if (move.contains(ANNOTATION_BEAT_MOVE)) {
//      String[] steps = move.split(ANNOTATION_BEAT_MOVE);
//      for (int i = 0; i < steps.length - 1; i++) {
//        Square firstStep, secondStep;
//        try {
//          firstStep = parseStep(steps[i]);
//          secondStep = parseStep(steps[i + 1]);
//        } catch (SquareNotFoundException e) {
//          GWT.log(e.getLocalizedMessage(), e);
//          continue;
//        }
//        Square captured = findCaptured(firstStep, secondStep);
//        if (null == captured) {
//          return;
//        }
//        capturedStack.push(captured);
//        move(firstStep, secondStep, captured, false, stepCursor);
//      }
//    }
//  }

//  public void moveEmulatedNextBlack(String move, int stepCursor) {
//    if (move.contains(ANNOTATION_SIMPLE_MOVE)) {
//      String[] steps = move.split(ANNOTATION_SIMPLE_MOVE);
//      Square startSquare, endSquare;
//      try {
//        startSquare = parseStep(steps[0]);
//        endSquare = parseStep(steps[1]);
//      } catch (SquareNotFoundException e) {
//        GWT.log(e.getLocalizedMessage(), e);
//        return;
//      }
//      move(startSquare, endSquare, null, false, stepCursor);
//    } else if (move.contains(ANNOTATION_BEAT_MOVE)) {
//      String[] steps = move.split(ANNOTATION_BEAT_MOVE);
//      for (int i = 0; i < steps.length - 1; i++) {
//        Square firstStep, secondStep;
//        try {
//          firstStep = parseStep(steps[i]);
//          secondStep = parseStep(steps[i + 1]);
//        } catch (SquareNotFoundException e) {
//          GWT.log(e.getLocalizedMessage(), e);
//          continue;
//        }
//        Square captured = findCaptured(firstStep, secondStep);
//        if (null == captured) {
//          return;
//        }
//        capturedStack.push(captured);
//        move(firstStep, secondStep, captured, false, stepCursor);
//      }
//    }
//  }

//  public void moveEmulatedPrevWhite(String move, int stepCursor) {
//    if (move.contains(ANNOTATION_SIMPLE_MOVE)) {
//      String[] steps = move.split(ANNOTATION_SIMPLE_MOVE);
//      Square startSquare = parseStep(steps[1]);
//      Square endSquare = parseStep(steps[0]);
//      move(startSquare, endSquare, null, false, stepCursor);
//    } else if (move.contains(ANNOTATION_BEAT_MOVE)) {
//      String[] steps = move.split(ANNOTATION_BEAT_MOVE);
//      for (int i = steps.length - 1; i > 0; i--) {
//        Square firstStep = parseStep(steps[i]);
//        Square secondStep = parseStep(steps[i - 1]);
////        Square captured = findCaptured(firstStep, secondStep);
//        Square captured = capturedStack.pop();
//        if (null == captured) {
//          return;
//        } else {
//          myDraughtList.add(addDraught(captured.getRow(), captured.getCol(), !white));
//        }
//        move(firstStep, secondStep, null, false, stepCursor);
//      }
//    }
//  }

//  public void moveEmulatedPrevBlack(String move, int stepCursor) {
//    if (move.contains(ANNOTATION_SIMPLE_MOVE)) {
//      String[] steps = move.split(ANNOTATION_SIMPLE_MOVE);
//      Square startSquare = parseStep(steps[1]);
//      Square endSquare = parseStep(steps[0]);
//      move(startSquare, endSquare, null, false, stepCursor);
//    } else if (move.contains(ANNOTATION_BEAT_MOVE)) {
//      String[] steps = move.split(ANNOTATION_BEAT_MOVE);
//      for (int i = steps.length - 1; i > 0; i--) {
//        Square firstStep = parseStep(steps[i]);
//        Square secondStep = parseStep(steps[i - 1]);
////        Square captured = findCaptured(firstStep, secondStep);
//        Square captured = capturedStack.pop();
//        if (null == captured) {
//          return;
//        } else {
//          myDraughtList.add(addDraught(captured.getRow(), captured.getCol(), white));
//        }
//        move(firstStep, secondStep, null, false, stepCursor);
//      }
//    }
//  }

  public void moveOpponent(MoveDto move) {
    moveOpponent(move, -1);
  }

  public void moveOpponent(MoveDto move, int stepCursor) {
    int sRow = move.getStartSquare().getRow();
    int sCol = move.getStartSquare().getCol();

    int eRow = move.getEndSquare().getRow();
    int eCol = move.getEndSquare().getCol();

//    Square sSquare, eSquare;
//    try {
//      sSquare = backgroundLayer.getSquare(sRow, sCol);
//      eSquare = backgroundLayer.getSquare(eRow, eCol);
//    } catch (SquareNotFoundException e) {
//      GWT.log(e.getLocalizedMessage(), e);
//      return;
//    }

    int startRow = rows - 1 - sRow;
    int startCol = cols - 1 - sCol;

    int endRow = rows - 1 - eRow;
    int endCol = cols - 1 - eCol;

    Square startSquare, endSquare;
    try {
      startSquare = backgroundLayer.getSquare(startRow, startCol);
      endSquare = backgroundLayer.getSquare(endRow, endCol);
    } catch (SquareNotFoundException e) {
      GWT.log(e.getLocalizedMessage(), e);
      return;
    }

    boolean simpleMove = move.isSimple();
    if (!move.isCancel()) {
//      String op = simpleMove ? ANNOTATION_SIMPLE_MOVE : ANNOTATION_BEAT_MOVE;
//      String move = sSquare.toNotation(!isWhite(), false, false)
//          + op
//          + eSquare.toNotation(!isWhite(), true, false);
      final boolean first = produceFirstMoveFlag(move, true);
      move.setNumber(move.getNumber())
          .setFirst(first);
      eventBus.fireEvent(new NotationMoveEvent(move));
    }

    Square captured = null;
    boolean nextCapture = false;
    if (!simpleMove) {
      int beatenRow = rows - 1 - move.getTakenSquare().getRow();
      int beatenCol = cols - 1 - move.getTakenSquare().getCol();
      nextCapture = move.isContinueBeat();
      try {
        captured = backgroundLayer.getSquare(beatenRow, beatenCol);
      } catch (SquareNotFoundException e) {
        GWT.log(e.getLocalizedMessage(), e);
        return;
      }
    }

    if (move.isCancel()) {
      move(endSquare, startSquare, captured, nextCapture, true, stepCursor); // отменяем ход
    } else {
      move(startSquare, endSquare, captured, nextCapture, false, stepCursor); // обычный ход опонента
    }
  }

  private void move(Square startSquare, Square endSquare, Square captured, boolean nextCapture, int stepCursor) {
    move(startSquare, endSquare, captured, nextCapture, false, stepCursor);
  }

  private void move(Square startSquare, Square endSquare, Square captured, boolean nextCapture, boolean cancelMove) {
    move(startSquare, endSquare, captured, nextCapture, cancelMove, -1);
  }

  private void move(Square startSquare, Square endSquare, Square captured, boolean nextCapture, boolean cancelMove,
                    int stepCursor) {
    final Draught occupant = startSquare.getOccupant();

    // вычисляем координаты для перемещения шашки относительно её центра
    occupant.updateShape();
    final double mouseMovedX = occupant.getX() + endSquare.getCenterX() - startSquare.getCenterX(); // + occupant.getMouseMovedX();
    final double mouseMovedY = occupant.getY() + endSquare.getCenterY() - startSquare.getCenterY(); // + occupant.getMouseMovedY();

    occupant.animate(AnimationTweener.LINEAR, new AnimationProperties(
        AnimationProperty.Properties.X(mouseMovedX),
        AnimationProperty.Properties.Y(mouseMovedY)
    ), moveDraughtDuration, new AnimationCallback() {
      @Override
      public void onClose(IAnimation animation, IAnimationHandle handle) {
        super.onClose(animation, handle);
        // if draught achieved start of desk mark it as Queen
        if (!occupant.isQueen()) {
          if (isEmulate()) {
            if (null != endSquare.getOccupant() && endSquare.getOccupant().isWhite() && 0 == endSquare.getRow()
                || null != endSquare.getOccupant() && !endSquare.getOccupant().isWhite()
                && (rows - 1) == endSquare.getRow()) {
              occupant.setPosition(endSquare.getRow(), endSquare.getCol());
              occupant.setQueen(true);
              queenStepStack.push(stepCursor);
            }
          } else if ((rows - 1) == endSquare.getRow() && occupant.isWhite() != white) {
            occupant.setPosition(endSquare.getRow(), endSquare.getCol());
            occupant.setQueen(true);
          }
        }
      }
    });

    endSquare.setOccupant(occupant);
    occupant.setPosition(endSquare.getRow(), endSquare.getCol());

    if (captured != null && !cancelMove) {
      removeDraughtFrom(captured);
    } else if (captured != null) {
      if (isMyTurn()) {
        myDraughtList.add(addDraught(captured.getRow(), captured.getCol(), isWhite()));
      } else {
        opponentDraughtList.add(addDraught(captured.getRow(), captured.getCol(), !isWhite()));
      }
      eventBus.fireEvent(new CheckWinnerEvent());
    }

    if (!nextCapture && !isEmulate()) {
      toggleTurn();
    }

    // переносим этот код из конца анимации сюда, потому что в ускоренной промотки он не выполняется.
    if (isEmulate() && occupant.isQueen()) {
      if ((null != endSquare.getOccupant() && endSquare.getOccupant().isWhite()
          && 0 == startSquare.getRow()
          || null != endSquare.getOccupant() && !endSquare.getOccupant().isWhite()
          && (rows - 1) == startSquare.getRow())
          && queenStepStack.peek() == stepCursor + 1) {
        occupant.setPosition(endSquare.getRow(), endSquare.getCol());
        occupant.setQueen(false);
        queenStepStack.pop();
      }
    }
    startSquare.setOccupant(null);
    highlightedSquares.clear();
  }

  public boolean isWhite() {
    return white;
  }

  public boolean isMyTurn() {
    return turn;
  }

  public boolean toggleTurn() {
    turn = !turn;
    eventBus.fireEvent(new TurnChangeEvent(turn));
    return turn;
  }

  public List<Draught> getMyDraughts() {
    return myDraughtList;
  }

  public List<Draught> getOpponentDraughts() {
    return opponentDraughtList;
  }

  public boolean isEmulate() {
    return emulate;
  }

  public void setEmulate(boolean emulate) {
    this.emulate = emulate;
  }

  public void moveDraught(double clickX, double clickY) {
    Draught selectedDraught = Draught.getSelectedDraught();
    if (selectedDraught != null && !highlightedSquares.isEmpty()) {
      Square endSquare = null, startSquare = null;
      try {
        endSquare = getSquare(clickX, clickY);
      } catch (SquareNotFoundException e) {
        GWT.log(e.getLocalizedMessage(), e);
      }

      try {
        startSquare = getSquare(selectedDraught.getRow(), selectedDraught.getCol());
      } catch (SquareNotFoundException e) {
        GWT.log(e.getLocalizedMessage(), e);
      }

      if (highlightedSquares.contains(endSquare) && startSquare != null && startSquare.isOnLine(endSquare)) {
        // получаем флаги передвижения и взятую шашку
        MoveDto move = move(startSquare, endSquare);
        move.setStartSquare(startSquare);
        move.setEndSquare(endSquare);

        final boolean first = produceFirstMoveFlag(move, false);
        move.setNumber(moveStack.size() + 1)
            .setFirst(first);

        boolean isSimpleMove = move.isSimple();
        GWT.log("SIMPLE MOVE " + isSimpleMove);
        if (isSimpleMove || move.isStopBeat()) {
          toggleTurn();
        }
        if (!selectedDraught.isQueen()) {
          if (selectedDraught.getRow() == 0) {
            selectedDraught.setQueen(true);
          }
        }

        if (endSquare != null) {
//          String op = isSimpleMove ? ANNOTATION_SIMPLE_MOVE : ANNOTATION_BEAT_MOVE;
//          String move = startSquare.toNotation(isWhite(), false, false)
//              + op
//              + endSquare.toNotation(isWhite(), true, false);
          eventBus.fireEvent(new NotationMoveEvent(move));
          eventBus.fireEvent(new PlayMoveEvent(move));
          moveStack.push(move);

          AnimationProperties props = new AnimationProperties();
          props.push(AnimationProperty.Properties.X(endSquare.getCenterX()));
          props.push(AnimationProperty.Properties.Y(endSquare.getCenterY()));

          selectedDraught.animate(AnimationTweener.LINEAR, props, 100);

          props = new AnimationProperties();
          props.push(AnimationProperty.Properties.SCALE(1.0));

          selectedDraught.animate(AnimationTweener.LINEAR, props, 100);
        }

        backgroundLayer.resetDeskDrawing();
      }
    }
  }

  private boolean produceFirstMoveFlag(MoveDto move, boolean opponent) {
    if (isWhite()) {
      return isMyTurn();
    } else {
      return !isMyTurn();
    }
  }

  public void clearDesk() {
    for (int i = 0; i < cols; i++) {
      for (int j = 0; j < rows; j++) {
        try {
          Square square = getSquare(i, j);
          removeDraughtFrom(square, true);
        } catch (SquareNotFoundException e) {
          GWT.log(e.getLocalizedMessage(), e);
        }
      }
    }
  }

  public void moveCanceled(MoveDto move) {
    int startRow = move.getStartSquare().getRow();
    int startCol = move.getStartSquare().getCol();

    int endRow = move.getEndSquare().getRow();
    int endCol = move.getEndSquare().getCol();

    Square startSquare, endSquare;
    try {
      startSquare = backgroundLayer.getSquare(startRow, startCol);
      endSquare = backgroundLayer.getSquare(endRow, endCol);
    } catch (SquareNotFoundException e) {
      GWT.log(e.getLocalizedMessage(), e);
      return;
    }

    boolean simpleMove = move.isSimple();
//    boolean cancelMove = capture.contains(CANCEL_MOVE);

    Square captured = null;
    boolean nextCapture = false;
    if (!simpleMove) {
      int beatenRow = move.getTakenSquare().getRow();
      int beatenCol = move.getTakenSquare().getCol();
      nextCapture = move.isContinueBeat();
      try {
        captured = backgroundLayer.getSquare(beatenRow, beatenCol);
      } catch (SquareNotFoundException e) {
        GWT.log(e.getLocalizedMessage(), e);
      }
    }

    move(endSquare, startSquare, captured, nextCapture, true);
    moveStack.pop();
  }

  public MoveDto getLastMove() {
    return moveStack.lastElement();
  }

  public boolean hasNoMoves() {
    return moveStack.isEmpty();
  }
}
