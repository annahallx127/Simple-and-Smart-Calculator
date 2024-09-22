import org.junit.Before;
import org.junit.Test;

import calculator.Calculator;
import calculator.SmartCalculator;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for the SmartCalculator class to verify its functionality and correctness.
 * These tests cover valid operations, edge cases, and error conditions to ensure the new correct
 * calculator behavior.
 * SmartCalculatorTest also extends an Abstract test class that shares common tests between
 * both Simple and Smart calculators.
 */
public class SmartCalculatorTest extends AbstractSimpleAndSmartCalculatorTest {

  @Before
  public void setUp() {
    calculator = new SmartCalculator();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStartOfOperationPlus() {
    Calculator newCalculator = calculator.input('+');

    newCalculator.input('=');
    assertEquals("", newCalculator.getResult());
  }

  @Test
  public void testOperatorBeforeEquals() {
    Calculator newCalculator = calculator.input('3').input('2').input('+').input('=');

    assertEquals("64", newCalculator.getResult());

  }

  @Test
  public void testLegalStartOfOperationPlus2() {
    Calculator newCalculator = calculator.input('+');

    assertEquals("", newCalculator.getResult());
  }

  @Test
  public void testNewOperatorIllegalOperationAfterEquals() {
    Calculator newCalculator = calculator.input('7');
    newCalculator = newCalculator.input('-').input('6').input('=');
    assertEquals("1", newCalculator.getResult());
    newCalculator = newCalculator.input('-').input('6').input('+').input('=');
    assertEquals("-10", newCalculator.getResult());
  }

  @Test
  public void testMultipleEquals() {
    Calculator newCalculator = calculator.input('7').input('+').input('2').input('=').input('=');
    assertEquals("11", newCalculator.getResult());
  }

  @Test
  public void testSubtractionAndMultipleEquals() {
    Calculator newCalculator = calculator.input('9');

    newCalculator = newCalculator.input('-').input('-');
    assertEquals("9-", newCalculator.getResult());

    newCalculator = newCalculator.input('2');
    assertEquals("9-2", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("7", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("5", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("3", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("1", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("-1", newCalculator.getResult());
  }

  @Test
  public void testAdditionAndMultipleEquals() {
    Calculator newCalculator = calculator.input('9');

    newCalculator = newCalculator.input('+').input('+').input('+');
    assertEquals("9+", newCalculator.getResult());

    newCalculator = newCalculator.input('2');
    assertEquals("9+2", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("11", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("13", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("15", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("17", newCalculator.getResult());
  }

  @Test
  public void testMultiplicationAndMultipleEquals() {
    Calculator newCalculator = calculator.input('9');

    newCalculator = newCalculator.input('*').input('+').input('*');
    assertEquals("9*", newCalculator.getResult());

    newCalculator = newCalculator.input('2');
    assertEquals("9*2", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("18", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("36", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("72", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("144", newCalculator.getResult());
  }

  @Test
  public void testValidSequenceOfMultipleOperators() {
    Calculator newCalculator = calculator.input('2');
    newCalculator = newCalculator.input('+');
    assertEquals("2+", newCalculator.getResult());

    newCalculator = newCalculator.input('*');
    assertEquals("2*", newCalculator.getResult());

    newCalculator = newCalculator.input('+');
    assertEquals("2+", newCalculator.getResult());

    newCalculator = newCalculator.input('-');
    assertEquals("2-", newCalculator.getResult());

    newCalculator = newCalculator.input('+');
    assertEquals("2+", newCalculator.getResult());

    newCalculator = newCalculator.input('+').input('*');
    assertEquals("2*", newCalculator.getResult());

    newCalculator = newCalculator.input('3');
    assertEquals("2*3", newCalculator.getResult());

    newCalculator = newCalculator.input('3').input('+').input('*').input('-').input('-').input('-');
    assertEquals("6-", newCalculator.getResult());

    newCalculator = newCalculator.input('3');
    assertEquals("6-3", newCalculator.getResult());
  }

  @Test
  public void testInputErrors() {
    Calculator newCalculator = calculator.input('9');

    newCalculator = newCalculator.input('*').input('2').input('+')
            .input('=').input('0').input('=');
    assertEquals("0", newCalculator.getResult());
  }

  @Test
  public void testLegalOperationSequenceMultipleOperators() {
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
    assertEquals("810-", newCalculator.getResult());
    newCalculator = newCalculator.input('1');
    assertEquals("810-1", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("809", newCalculator.getResult());
  }

  @Test
  public void testClearAfterOperatorFirst() {
    Calculator newCalculator = calculator.input('2');

    newCalculator = newCalculator.input('+').input('C').input('+');
    assertEquals("", newCalculator.getResult());
  }

  @Test
  public void testMultipleEqualsForSubtraction() {
    Calculator newCalculator = calculator.input('2').input('0').input('0');

    assertEquals("200", newCalculator.getResult());
    newCalculator = newCalculator.input('-').input('1').input('=');
    assertEquals("199", newCalculator.getResult());
    newCalculator = newCalculator.input('-');
    assertEquals("199-", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("0", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("-199", newCalculator.getResult());
    newCalculator = newCalculator.input('-');
    assertEquals("-199-", newCalculator.getResult());
    newCalculator = newCalculator.input('-');
    assertEquals("-199-", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("0", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("199", newCalculator.getResult());
  }

  @Test
  public void testMultipleEqualsForMultiplication() {
    Calculator newCalculator = calculator.input('2').input('0');

    assertEquals("20", newCalculator.getResult());
    newCalculator = newCalculator.input('*').input('2').input('=');
    assertEquals("40", newCalculator.getResult());
    newCalculator = newCalculator.input('*');
    assertEquals("40*", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("1600", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("64000", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("2560000", newCalculator.getResult());

    newCalculator = newCalculator.input('-').input('=');
    assertEquals("0", newCalculator.getResult());

  }

  @Test
  public void testMultipleEqualsForAddition() {
    Calculator newCalculator = calculator.input('2').input('0');

    assertEquals("20", newCalculator.getResult());
    newCalculator = newCalculator.input('+').input('2').input('=');
    assertEquals("22", newCalculator.getResult());
    newCalculator = newCalculator.input('+');
    assertEquals("22+", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("44", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("66", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("88", newCalculator.getResult());
    newCalculator = newCalculator.input('+').input('+');
    assertEquals("88+", newCalculator.getResult());
    newCalculator = newCalculator.input('+').input('=');
    assertEquals("176", newCalculator.getResult());
  }

  @Test
  public void testMultipleEqualsChangingOperators() {
    Calculator newCalculator = calculator.input('2').input('0');

    assertEquals("20", newCalculator.getResult());
    newCalculator = newCalculator.input('+').input('2').input('=');
    assertEquals("22", newCalculator.getResult());
    newCalculator = newCalculator.input('-');
    assertEquals("22-", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("0", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("-22", newCalculator.getResult());
    newCalculator = newCalculator.input('+');
    assertEquals("-22+", newCalculator.getResult());
    newCalculator = newCalculator.input('=').input('=');
    assertEquals("-66", newCalculator.getResult());
    newCalculator = newCalculator.input('*').input('=');
    assertEquals("4356", newCalculator.getResult());
  }


  @Test
  public void testSkippingTheSecondOperandPlus() {
    Calculator newCalculator = calculator.input('2').input('4').input('+').input('=');
    assertEquals("48", newCalculator.getResult());
    newCalculator = newCalculator.input('=').input('+');
    assertEquals("72+", newCalculator.getResult());
    newCalculator = newCalculator.input('-');
    assertEquals("72-", newCalculator.getResult());
    newCalculator = newCalculator.input('+');
    assertEquals("72+", newCalculator.getResult());
    newCalculator = newCalculator.input('+').input('*').input('*').input('*').input('+');
    assertEquals("72+", newCalculator.getResult());
    newCalculator = newCalculator.input('+').input('1').input('2').input('3').input('=');
    assertEquals("195", newCalculator.getResult());
  }

  @Test
  public void testSkippingTheSecondOperandPlus2() {
    Calculator newCalculator = calculator.input('2').input('4').input('+').input('=');
    assertEquals("48", newCalculator.getResult());
    newCalculator = newCalculator.input('=').input('+');
    assertEquals("72+", newCalculator.getResult());
    newCalculator = newCalculator.input('-');
    assertEquals("72-", newCalculator.getResult());
    newCalculator = newCalculator.input('+');
    assertEquals("72+", newCalculator.getResult());
    newCalculator = newCalculator.input('=').input('=').input('=').input('*').input('+');
    assertEquals("288+", newCalculator.getResult());
    newCalculator = newCalculator.input('+').input('1').input('2').input('3').input('=');
    assertEquals("411", newCalculator.getResult());
  }

  @Test
  public void testSkippingTheSecondOperandMinus() {
    Calculator newCalculator = calculator.input('2').input('4').input('-').input('=');
    assertEquals("0", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("-24", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("-48", newCalculator.getResult());

    newCalculator = newCalculator.input('-').input('2');
    assertEquals("-48-2", newCalculator.getResult());

    newCalculator = newCalculator.input('+').input('=');
    assertEquals("-100", newCalculator.getResult());
  }

  @Test
  public void testOperation() {
    Calculator newCalculator = calculator.input('4').input('4').input('+').input('=');
    assertEquals("88", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("132", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("176", newCalculator.getResult());

    newCalculator = newCalculator.input('-').input('2');
    assertEquals("176-2", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("174", newCalculator.getResult());

    newCalculator = newCalculator.input('+').input('=');
    assertEquals("348", newCalculator.getResult());
  }

  @Test
  public void testNewOperationAfterEqualsSmart() {
    Calculator newCalculator = calculator.input('4').input('4').input('+').input('=');
    assertEquals("88", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("132", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("176", newCalculator.getResult());

    newCalculator = newCalculator.input('2');
    assertEquals("2", newCalculator.getResult());

    newCalculator = newCalculator.input('=');
    assertEquals("2", newCalculator.getResult());

    newCalculator = newCalculator.input('+').input('=');
    assertEquals("4", newCalculator.getResult());
  }

  @Test
  public void testOperatorAfterEquals() {
    Calculator newCalculator = calculator.input('2').input('=').input('+').input('2')
            .input('+').input('=');
    assertEquals("8", newCalculator.getResult());
  }

  @Test
  public void testSkippingTheSecondOperandMultiply() {
    Calculator newCalculator = calculator.input('2').input('4').input('*').input('=');

    assertEquals("576", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("13824", newCalculator.getResult());
  }

  @Test
  public void testOperatorAfterEquals2() {
    Calculator newCalculator = calculator.input('2').input('4').input('*')
            .input('=').input('+').input('-').input('1').input('=');
    assertEquals("575", newCalculator.getResult());
  }

  @Test
  public void testBeginWithPlus() {
    Calculator newCalculator = calculator.input('+').input('3').input('2');
    assertEquals("32", newCalculator.getResult());
  }

  @Test
  public void testBeginWithPlus2() {
    Calculator newCalculator = calculator.input('+');
    assertEquals("", newCalculator.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBeginWithPlus3() {
    Calculator newCalculator = calculator.input('+').input('-');
    assertEquals("", newCalculator.getResult());
  }

  @Test
  public void testBeginWithPlus4() {
    Calculator newCalculator = calculator.input('+').input('+').input('+').input('+').input('+');
    assertEquals("", newCalculator.getResult());
  }

  @Test
  public void testBeginWithPlus5() {
    Calculator newCalculator = calculator.input('+').input('+').input('+').input('+').input('+');
    assertEquals("", newCalculator.getResult());
    newCalculator = newCalculator.input('C');
    assertEquals("", newCalculator.getResult());
    newCalculator = newCalculator.input('+').input('2').input('-').input('5').input('=');
    assertEquals("-3", newCalculator.getResult());
  }

  @Test
  public void testBeginWithPlusOperation() {
    Calculator newCalculator = calculator.input('+').input('1').input('0').input('+');
    assertEquals("10+", newCalculator.getResult());
    newCalculator = newCalculator.input('5');
    assertEquals("10+5", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("15", newCalculator.getResult());
  }


  @Test
  public void testZeroPlus() {
    Calculator newCalculator = calculator.input('0').input('+');
    assertEquals("0+", newCalculator.getResult());
    newCalculator = newCalculator.input('1');
    assertEquals("0+1", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("1", newCalculator.getResult());
  }

  @Test
  public void testValidOperation() {
    Calculator newCalculator = calculator.input('+').input('3').input('2').input('+').input('-')
            .input('2').input('4').input('=');
    assertEquals("8", newCalculator.getResult());
  }


}