package net.rushashki.social.shashki64.shashki.dto;

import net.rushashki.social.shashki64.shared.model.Move;
import net.rushashki.social.shashki64.shashki.Square;

import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: alekspo
 * Date: 02.09.15
 * Time: 17:25
 */
public class MoveDto implements Move {
  private static final String SIMPLE_MOVE_SEP = "-";
  private static final String BEAT_MOVE_SEP = ":";

  private int number;
  private boolean first; // первый ход в паре ходов. Например, ee-aa в ee-aa bb-cc
  private boolean canceled;

  private Square startSquare;
  private Square endSquare;
  private Square takenSquare;
  private Set<MoveFlags> moveFlags = new HashSet<>();

  public MoveDto() {
  }

  public MoveDto(Move move) {
    this.number = move.getNumber();
    this.first = move.isFirst();
    this.startSquare = Square.getInstance(move.getStartPos());
    this.endSquare = Square.getInstance(move.getEndPos());
    this.takenSquare = Square.getInstance(move.getTakenPos());
    this.moveFlags = move.getMoveFlags();
  }

  public int getNumber() {
    return number;
  }

  public Move setNumber(int number) {
    this.number = number;
    return this;
  }

  public boolean isFirst() {
    return first;
  }

  public Move setFirst(boolean first) {
    this.first = first;
    return this;
  }

  public String getTakenPos() {
    if (takenSquare == null) {
      return "";
    }
    return takenSquare.toSend();
  }

  public Move setTakenSquare(Square takenSquare) {
    this.takenSquare = takenSquare;
    return this;
  }

  public Square getTakenSquare() {
    return takenSquare;
  }

  public String getStartPos() {
    if (startSquare == null) {
      return "";
    }
    return startSquare.toSend();
  }

  public Square getStartSquare() {
    return startSquare;
  }

  public Move setStartSquare(Square startSquare) {
    this.startSquare = startSquare;
    return this;
  }

  public String getEndPos() {
    if (endSquare == null) {
      return "";
    }
    return endSquare.toSend();
  }

  public Square getEndSquare() {
    return endSquare;
  }

  public Move setEndSquare(Square endSquare) {
    this.endSquare = endSquare;
    return this;
  }

  public boolean isCancel() {
    return moveFlags.contains(MoveFlags.CANCEL_MOVE);
  }

  public void turnOnCancelMove() {
    moveFlags.add(MoveFlags.CANCEL_MOVE);
  }

  public void turnOffCancelMove() {
    moveFlags.remove(MoveFlags.CANCEL_MOVE);
  }

  public boolean isSimple() {
    return moveFlags.contains(MoveFlags.SIMPLE_MOVE);
  }

  public void turnOnSimpleMove() {
    moveFlags.add(MoveFlags.SIMPLE_MOVE);
  }

  public void turnOffSimpleMove() {
    moveFlags.remove(MoveFlags.SIMPLE_MOVE);
  }

  public boolean isContinueBeat() {
    return moveFlags.contains(MoveFlags.CONTINUE_BEAT);
  }

  public void turnOnContinueBeat() {
    moveFlags.add(MoveFlags.CONTINUE_BEAT);
  }

  public void turnOffContinueBeat() {
    moveFlags.remove(MoveFlags.CONTINUE_BEAT);
  }

  public boolean isStopBeat() {
    return moveFlags.contains(MoveFlags.STOP_BEAT);
  }

  public void turnOnStopBeat() {
    moveFlags.add(MoveFlags.STOP_BEAT);
  }

  public void turnOffStopBeat() {
    moveFlags.remove(MoveFlags.STOP_BEAT);
  }

  public Set<MoveFlags> getMoveFlags() {
    return moveFlags;
  }

  public String toNotation() {
    return isSimple() ? startSquare.toNotation(first) + SIMPLE_MOVE_SEP + endSquare.toNotation(first)
        : startSquare.toNotation(first) + BEAT_MOVE_SEP + endSquare.toNotation(first);
  }

  @Override
  public String toString() {
    return "MoveDto{" +
        "number=" + number +
        ", first=" + first +
        ", startSquare=" + startSquare +
        ", endSquare=" + endSquare +
        ", takenSquare=" + takenSquare +
        ", moveFlags=" + moveFlags +
        '}';
  }
}
