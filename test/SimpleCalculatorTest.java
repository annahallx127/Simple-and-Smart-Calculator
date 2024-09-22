import org.junit.Before;
import org.junit.Test;

import calculator.Calculator;
import calculator.SimpleCalculator;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the SimpleCalculator class to verify its functionality and correctness.
 * These tests cover valid operations, edge cases, and error conditions to ensure correct
 * calculator behavior.
 */
public class SimpleCalculatorTest extends AbstractSimpleAndSmartCalculatorTest {

  @Before
  public void setUp() {
    calculator = new SimpleCalculator();
  }

  @Test
  public void testMultipleEquals() {
    Calculator newCalculator = calculator.input('5');
    newCalculator = newCalculator.input('-').input('2').input('=').input('=');
    assertEquals("3", newCalculator.getResult());
  }

  @Test
  public void testMultipleEqualsWithOperation() {
    Calculator newCalculator = calculator.input('5');
    newCalculator = newCalculator.input('-').input('2').input('=').input('=')
            .input('=').input('=').input('=').input('=')
            .input('*').input('6').input('=');
    assertEquals("18", newCalculator.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputErrors() {
    Calculator newCalculator = calculator.input('9');

    newCalculator = newCalculator.input('*').input('2').input('+')
            .input('=').input('0').input('=');
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalOperationInValidOperation() {
    Calculator newCalculator = calculator.input('9');

    assertEquals("9", newCalculator.getResult());
    newCalculator = newCalculator.input('0');
    assertEquals("90", newCalculator.getResult());
    newCalculator = newCalculator.input('*');
    assertEquals("90*", newCalculator.getResult());
    newCalculator = newCalculator.input('9');
    assertEquals("90*9", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("810", newCalculator.getResult());
    newCalculator = newCalculator.input('*');
    assertEquals("810*", newCalculator.getResult());
    newCalculator = newCalculator.input('-');
    assertEquals("810*-", newCalculator.getResult());
    newCalculator = newCalculator.input('1');
    assertEquals("810*-1", newCalculator.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultipleOperatorsInvalid() {
    Calculator newCalculator = calculator.input('5');

    newCalculator = newCalculator.input('-').input('+').input('3').input('=');
    assertEquals("", newCalculator.getResult());
  }

  @Test
  public void testMultipleOperatorsInSequenceValid() {
    Calculator newCalculator = calculator.input('1');

    newCalculator = newCalculator.input('3').input('+').input('3').input('*')
            .input('2').input('-').input('6').input('=');
    assertEquals("26", newCalculator.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIncompleteOperation() {
    Calculator newCalculator = calculator.input('9');

    newCalculator = newCalculator.input('9').input('6').input('+').input('=');
  }


  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStartOfOperation() {
    Calculator newCalculator = calculator.input('+');

    newCalculator.input('=');
  }


  @Test(expected = IllegalArgumentException.class)
  public void testDoubleNegative() {
    Calculator newCalculator = calculator.input('9');

    newCalculator = newCalculator.input('-').input('-').input('2').input('=').input('=');
  }

}

