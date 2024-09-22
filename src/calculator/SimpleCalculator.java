package calculator;

/**
 * A simple calculator for whole numbers, supporting addition, subtraction, and multiplication.
 * Processes input character by character, returning new instances to maintain immutability and
 * display the result.
 * Extends the AbstractCalculator and Implements the Calculator interface.
 */
public class SimpleCalculator extends AbstractCalculator<SimpleCalculator> implements Calculator {

  /**
   * Initializes a new SimpleCalculator with default values supered from its parent class.
   * The calculator is set to an initial state with zeroed operands,
   * no current operation, and an empty display.
   */
  public SimpleCalculator() {
    super();
  }

  private SimpleCalculator(long currentOperand, char currentOperation, long result,
                           StringBuilder display, int maxValue, boolean lastInputWasEqual,
                           boolean lastInputWasOperator) {
    super(currentOperand, currentOperation, result, display, maxValue,
            lastInputWasEqual, lastInputWasOperator);
  }

  @Override
  protected SimpleCalculator createCalculator(long currentOperand, char currentOperation,
                                              long result, StringBuilder display, int maxValue,
                                              boolean lastInputWasEqual,
                                              boolean lastInputWasOperator) {
    return new SimpleCalculator(currentOperand, currentOperation, result, display,
            maxValue, lastInputWasEqual, lastInputWasOperator);
  }

  @Override
  public SimpleCalculator input(char input) {
    if (input == 'C') {
      return clear();
    }

    if (input == '0' && display.length() == 0 && lastInputWasOperator) {
      return new SimpleCalculator(0, currentOperation, result, display, maxValue,
              lastInputWasEqual, false);
    }

    if (display.length() == 0 && (input == '*' || input == '-')) {
      throw new IllegalArgumentException("Operation cannot start with this operator");
    }

    if (input == '=' && currentOperation == '\0' && !lastInputWasEqual) {
      if (display.length() > 0) {
        display.setLength(0);
        display.append(result);
      } else if (display.length() == 0) {
        throw new IllegalArgumentException("Operation cannot start with =");
      }
      return new SimpleCalculator(currentOperand, currentOperation, result, display, maxValue,
              true, lastInputWasOperator);
    }

    if (Character.isDigit(input)) {
      if (lastInputWasEqual) {
        return new SimpleCalculator().input(input);
      }
      SimpleCalculator newCalc = isValidDigit(input);
      return new SimpleCalculator(newCalc.currentOperand,
              newCalc.currentOperation, newCalc.result, newCalc.display, newCalc.maxValue,
              false, false);
    } else {
      switch (input) {
        case '+':
        case '-':
        case '*':
          if (lastInputWasOperator) {
            throw new IllegalArgumentException("Cannot input two operators in a row");
          }
          if (currentOperation != '\0') {
            SimpleCalculator newCalc = performCalculation();
            return new SimpleCalculator(newCalc.currentOperand,
                    newCalc.currentOperation, newCalc.result, newCalc.display, newCalc.maxValue,
                    lastInputWasEqual, false)
                    .prepareOperation(input);
          }
          return prepareOperation(input);
        case '=':
          if (lastInputWasOperator) {
            throw new IllegalArgumentException("Incomplete Operation");
          }
          if (!lastInputWasEqual) {
            SimpleCalculator newCalc = performCalculation();
            return new SimpleCalculator(newCalc.currentOperand,
                    newCalc.currentOperation, newCalc.result,
                    newCalc.display, newCalc.maxValue,
                    true, false);
          }
          return new SimpleCalculator(currentOperand, currentOperation, result, display, maxValue,
                  true, false);
        default:
          throw new IllegalArgumentException("Invalid input: " + input);
      }
    }
  }

  private SimpleCalculator performCalculation() {
    long tempResult;
    switch (currentOperation) {
      case '+':
        tempResult = result + currentOperand;
        break;
      case '-':
        tempResult = result - currentOperand;
        break;
      case '*':
        tempResult = result * currentOperand;
        break;
      default:
        tempResult = result;
    }

    if (tempResult > maxValue || tempResult < Integer.MIN_VALUE) {
      tempResult = 0;
    }

    StringBuilder newDisplay = new StringBuilder();
    newDisplay.append(tempResult);

    return new SimpleCalculator(0, '\0',
            tempResult, newDisplay, maxValue, false, false);
  }
}
