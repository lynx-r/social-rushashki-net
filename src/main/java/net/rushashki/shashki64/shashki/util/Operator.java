package net.rushashki.shashki64.shashki.util;

/**
 * Created with IntelliJ IDEA.
 * Profile: alekspo
 * Date: 18.12.13
 * Time: 20:52
 */
public enum Operator
{

  ADDITION(Operators.ADD) {
    @Override
    public int apply(int x, int n) {
      return x + n;
    }
  },
  SUBTRACTION(Operators.SUB) {
    @Override
    public int apply(int x, int n) {
      return x - n;
    }
  };
  // You'd include other operators too...

  private final Operators operator;
//  private boolean inverted;

  private Operator(Operators operator) {
    this.operator = operator;
  }

  // Yes, enums *can* have abstract methods. This code compiles...
  public abstract int apply(int x, int n);

  public boolean equals(Operators operator) {
    return this.operator.equals(operator);
  }
}