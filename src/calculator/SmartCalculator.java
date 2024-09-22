package calculator;

/**
 * A simple calculator for whole numbers, supporting addition, subtraction, and multiplication.
 * Processes input character by character, returning new instances to maintain immutability and
 * display the result. Now supports new complex operations similar to the Windows 11 calculator.
 * Extends the AbstractCalculator and Implements the Calculator interface.
 */
public class SmartCalculator extends AbstractCalculator<SmartCalculator> implements Calculator {

  private final long lastOperand;

  /**
   * Initializes a new SmartCalculator with default values called from parent class.
   * The calculator is set to an initial state with zeroed operands,
   * no current operation, and an empty display.
   */
  public SmartCalculator() {
    super();
    this.lastOperand = 0;
  }

  // making a private SmartCalculator with the necessary default statuses
  // ensures immutability, allowing methods to call this constructor instead
  private SmartCalculator(long currentOperand, char currentOperation, long result,
                          StringBuilder display, int maxValue, boolean lastInputWasEqual,
                          boolean lastInputWasOperator, long lastOperand) {
    super(currentOperand, currentOperation, result, display, maxValue, lastInputWasEqual,
            lastInputWasOperator);
    this.lastOperand = lastOperand;
  }

  @Override
  protected SmartCalculator createCalculator(long currentOperand, char currentOperation,
                                             long result, StringBuilder display, int maxValue,
                                             boolean lastInputWasEqual,
                                             boolean lastInputWasOperator) {
    return new SmartCalculator(currentOperand, currentOperation, result, display, maxValue,
            lastInputWasEqual, lastInputWasOperator, lastOperand);
  }

  @Override
  public SmartCalculator input(char input) {
    if (input == 'C') {
      return clear();
    }

    if (input == '0' && display.length() == 0 && lastInputWasOperator) {
      return new SmartCalculator(0, currentOperation, result, display, maxValue,
              lastInputWasEqual, false, lastOperand);
    }

    if (input == '=') {
      if (display.length() == 0 && result == 0) {
        throw new IllegalArgumentException("Operation cannot start with =");
      }
      SmartCalculator newCalc = performCalculation();
      return new SmartCalculator(newCalc.currentOperand, newCalc.currentOperation, newCalc.result,
              newCalc.display, newCalc.maxValue, true, false,
              newCalc.lastOperand);
    }

    if (display.length() == 0 && (input == '*' || input == '-')) {
      throw new IllegalArgumentException("Operation cannot start with the operator");
    }

    if (Character.isDigit(input)) {
      if (lastInputWasEqual) {
        return new SmartCalculator().input(input);
      }
      SmartCalculator newCalc = isValidDigit(input);
      return new SmartCalculator(newCalc.currentOperand, newCalc.currentOperation, newCalc.result,
              newCalc.display, newCalc.maxValue, false, false, newCalc.lastOperand);
    } else {
      switch (input) {
        case '+':
        case '-':
        case '*':
          if (lastInputWasEqual) {
            return new SmartCalculator(0, input, result,
                    new StringBuilder(String.valueOf(result)).append(input), maxValue,
                    false, true, result);
          }
          if (display.length() == 0) {
            return new SmartCalculator(currentOperand, currentOperation, result, display, maxValue,
                    false, false, lastOperand);
          }
          if (lastInputWasOperator) {
            display.setLength(display.length() - 1);
            display.append(input);
            return new SmartCalculator(currentOperand, input, result, display, maxValue,
                    lastInputWasEqual, true, lastOperand);
          }
          if (currentOperation != '\0') {
            SmartCalculator newCalc = performCalculation();
            return new SmartCalculator(newCalc.currentOperand, input, newCalc.result,
                    newCalc.display, newCalc.maxValue, lastInputWasEqual, true,
                    newCalc.currentOperand);
          }
          return prepareOperation(input);
        default:
          throw new IllegalArgumentException("Invalid input: " + input);
      }
    }
  }

  private SmartCalculator performCalculation() {
    long tempResult;
    long operandToUse =
            lastInputWasOperator ? result : (lastInputWasEqual ? lastOperand : currentOperand);

    switch (currentOperation) {
      case '+':
        tempResult = result + operandToUse;
        break;
      case '-':
        tempResult = result - operandToUse;
        break;
      case '*':
        tempResult = result * operandToUse;
        break;
      default:
        tempResult = result;
        break;
    }
    if (tempResult > maxValue || tempResult < Integer.MIN_VALUE) {
      tempResult = 0;
    }

    StringBuilder newDisplay = new StringBuilder();
    newDisplay.append(tempResult);

    return new SmartCalculator(0, currentOperation, tempResult, newDisplay, maxValue,
            true, false, operandToUse);
  }
}
