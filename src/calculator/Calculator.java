package calculator;

/**
 * Interface for a basic calculator that performs arithmetic operations
 * and maintains its state based on character inputs.
 * Contains methods input() and getResult().
 */
public interface Calculator {
  /**
   * Updates the calculator state based on the provided character input.
   * This method processes digits, operators, and control commands to perform
   * arithmetic operations or modify the calculator's state.
   *
   * @param input a single character that is entered in the calculator
   * @return a new Calculator instance updated with the result of processing the input
   */
  Calculator input(char input);

  /**
   * This method provides a string representation of the calculator's state,
   * including any partial inputs or the computed result after operations.
   * The result reflects the latest arithmetic operation or the ongoing input sequence.
   *
   * @return the current display of the calculator as a String
   */
  String getResult();
}
