package calculator;

/**
 * AbstractCalculator provides a generic foundation for calculators, handling common tasks
 * such as processing digits and operations while ensuring immutability.
 * It serves as a base class for specific calculator implementations, the type T allows for
 * both Smart and Simple Calculator to extend this class.
 * It also contains the common code found in both subclasses.
 *
 * @param <T> the type of the concrete calculator class extending this abstract class
 */
public abstract class AbstractCalculator<T> implements Calculator {

  protected long currentOperand;
  protected char currentOperation;
  protected long result;
  protected StringBuilder display;
  protected int maxValue;
  protected boolean lastInputWasEqual;
  protected boolean lastInputWasOperator;

  protected AbstractCalculator() {
    this.currentOperand = 0;
    this.result = 0;
    this.display = new StringBuilder();
    this.maxValue = Integer.MAX_VALUE;
    this.currentOperation = '\0';
    this.lastInputWasEqual = false;
    this.lastInputWasOperator = false;
  }

  protected AbstractCalculator(long currentOperand, char currentOperation, long result,
                               StringBuilder display, int maxValue, boolean lastInputWasEqual,
                               boolean lastInputWasOperator) {
    this.currentOperand = currentOperand;
    this.currentOperation = currentOperation;
    this.result = result;
    this.display = new StringBuilder(display);
    this.maxValue = maxValue;
    this.lastInputWasEqual = lastInputWasEqual;
    this.lastInputWasOperator = lastInputWasOperator;
  }


  /**
   * createCalculator allows both Smart and Simple calculators to use this abstract class,
   * allowing the Abstract class to not explicitly return either or.
   *
   * @param currentOperand of the respective calculator
   * @param currentOperation of the respective calculator
   * @param result of the respective calculator
   * @param display of the respective calculator
   * @param maxValue of the respective calculator
   * @param lastInputWasEqual of the respective calculator
   * @param lastInputWasOperator of the respective calculator
   * @return a new instance of the respective calculator
   */
  protected abstract T createCalculator(long currentOperand, char currentOperation,
                                        long result, StringBuilder display, int maxValue,
                                        boolean lastInputWasEqual, boolean lastInputWasOperator);

  protected T clear() {
    return createCalculator(0, '\0', 0, new StringBuilder(),
            maxValue, false, false);
  }

  @Override
  public Calculator input(char input) {
    return null;
  }

  @Override
  public String getResult() {
    return display.toString();
  }

  protected T isValidDigit(char digit) {
    long newOperand = currentOperand;
    long newResult = result;
    StringBuilder newDisplay = new StringBuilder(display);

    String digitStr = Character.toString(digit);

    if (currentOperation != '\0') {
      String newOperandStr = currentOperand == 0 ? digitStr : (currentOperand) + digitStr;
      newOperand = Long.parseLong(newOperandStr);
      if (newOperand > maxValue) {
        throw new IllegalArgumentException("Operand exceeds maximum limit.");
      }
    } else {
      String newResultStr = result == 0 ? digitStr : (result) + digitStr;
      newResult = Long.parseLong(newResultStr);
      if (newResult > maxValue) {
        throw new IllegalArgumentException("Operation value exceeds maximum limit.");
      }
    }
    if (newDisplay.toString().equals("0")) {
      newDisplay.setLength(0);
    }
    newDisplay.append(digit);
    return createCalculator(newOperand, currentOperation, newResult, newDisplay,
            maxValue, false, false);
  }

  protected T prepareOperation(char operation) {
    StringBuilder newDisplay = new StringBuilder(display);
    newDisplay.append(operation);

    return createCalculator(0, operation, result, newDisplay, maxValue,
            false, true);
  }
}
