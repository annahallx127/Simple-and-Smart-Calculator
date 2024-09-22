import org.junit.Test;

import calculator.Calculator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests shared between SimpleCalculator and SmartCalculator.
 * Tests the basic functionality of both calculators with addition, subtraction,
 * and multiplication. Tests also include display updates, max number testing, and zero testing.
 * All the while ensuring any restricted operations are not allowed.
 * Both SimpleCalculatorTest and SmartCalculatorTest extend this abstract test class.
 */
public abstract class AbstractSimpleAndSmartCalculatorTest {
  protected Calculator calculator;

  @Test
  public void testValidOperation() {
    Calculator newInput = calculator.input('5');
    newInput = newInput.input('2').input('+').input('5').input('2')
            .input('+').input('3').input('=');

    assertEquals("107", newInput.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStartNegative() {
    Calculator newCalculator = calculator.input('-');

    newCalculator = newCalculator.input('2').input('=');
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCharacters() {
    calculator.input('/');
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCharacters2() {
    calculator.input('_');
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalCharacters3() {
    calculator.input('#');
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperationDuringOverflow() {
    Calculator newCalculator = calculator.input('1');

    try {
      newCalculator = newCalculator.input('1').input('1').input('1').input('1').input('1')
              .input('1').input('1').input('1').input('1').input('1');
      assertEquals("1111111111", newCalculator.getResult());

      newCalculator.input('1');
    } catch (IllegalArgumentException e) {
      newCalculator = newCalculator.input('1').input('1').input('1').input('1').input('1')
              .input('1').input('1').input('1').input('1').input('1');
      assertEquals("1111111111", newCalculator.getResult());
      calculator.input('+').input('7').input('=');
      assertEquals("1111111118", newCalculator.getResult());
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOverMaximumNumber() {
    Calculator newCalculator = calculator.input('1');

    newCalculator = newCalculator.input('1').input('1').input('1').input('1')
            .input('1').input('1').input('1').input('1').input('1')
            .input('1').input('1').input('1').input('1').input('=');
    assertEquals("11111111111111", newCalculator.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testJustBeforeMaximum() {
    Calculator newCalculator = calculator.input('1');

    newCalculator = newCalculator.input('1').input('1').input('1').input('1')
            .input('1').input('1').input('1').input('1').input('1')
            .input('1').input('1').input('1');
    assertEquals("1111111111111", newCalculator.getResult());
    newCalculator.input('1');
  }

  @Test
  public void testOverflowMultiplication() {
    Calculator newCalculator = calculator.input('1');

    newCalculator = newCalculator.input('1').input('1').input('1').input('1')
            .input('1').input('1').input('1').input('1').input('1')
            .input('*').input('2').input('0').input('0').input('0').input('=');
    assertEquals("0", newCalculator.getResult());

  }

  @Test
  public void testAfterMaxNumOperationItContinues() {
    Calculator newCalculator = calculator.input('2');

    newCalculator = newCalculator.input('1').input('4').input('7').input('4')
            .input('8').input('3').input('6').input('4').input('6')
            .input('+').input('2').input('+').input('3');
    assertEquals("0+3", newCalculator.getResult());
  }

  @Test
  public void testAfterMaxNumOperationItEquals() {
    Calculator newCalculator = calculator.input('2');

    newCalculator = newCalculator.input('1').input('4').input('7').input('4')
            .input('8').input('3').input('6').input('4').input('6')
            .input('+').input('2').input('+').input('3').input('=');
    assertEquals("3", newCalculator.getResult());
  }

  @Test
  public void testOperateOnMaxNumAddition() {
    Calculator newCalculator = calculator.input('2');

    newCalculator = newCalculator.input('1').input('4').input('7').input('4')
            .input('8').input('3').input('6').input('4').input('6')
            .input('+').input('2').input('=');
    assertEquals("0", newCalculator.getResult());
  }

  @Test
  public void testMaxNumEdgeCase() {
    Calculator newCalculator = calculator.input('2');

    newCalculator = newCalculator.input('1').input('4').input('7').input('4')
            .input('8').input('3').input('6').input('4').input('6')
            .input('+').input('1').input('=');
    assertEquals("2147483647", newCalculator.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStartOfOperation2() {
    Calculator newCalculator = calculator.input('-');

    newCalculator.input('=');
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalStartOfOperation3() {
    Calculator newCalculator = calculator.input('*');

    newCalculator.input('=');
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNegativeSequenceStart() {
    Calculator newCalculator = calculator.input('-');

    newCalculator = newCalculator.input('-').input('6').input('+').input('6').input('=');
  }

  @Test
  public void testNewOperandAfterEquals() {

    Calculator newCalculator = calculator.input('5');

    newCalculator = newCalculator.input('2').input('+').input('3').input('=');
    assertEquals("55", newCalculator.getResult());
    newCalculator = newCalculator.input('+').input('5').input('-').input('3').input('=');
    assertEquals("57", newCalculator.getResult());
  }

  @Test
  public void testClearAfterOperation() {
    Calculator newCalculator = calculator.input('5');

    newCalculator = newCalculator.input('2').input('+').input('3').input('=');
    newCalculator = newCalculator.input('C');
    assertEquals("", newCalculator.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEqualsAfterClear() {
    Calculator newCalculator = calculator.input('2');

    newCalculator = newCalculator.input('+').input('C').input('=');
    assertEquals("", newCalculator.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperatorAfterClear() {
    Calculator newCalculator = calculator.input('9');

    newCalculator = newCalculator.input('+').input('C').input('-');
    assertEquals("-", newCalculator.getResult());
  }

  @Test
  public void testClearAfterOverflow() {
    Calculator newCalculator = calculator.input('2');

    newCalculator = newCalculator.input('1').input('4').input('7').input('4')
            .input('8').input('3').input('6').input('4').input('6')
            .input('+').input('2').input('C');
    assertEquals("", newCalculator.getResult());
  }

  @Test
  public void testClearMiddleOfOperation() {
    Calculator newCalculator = calculator.input('5');

    newCalculator = newCalculator.input('2').input('+').input('3').input('C');
    assertEquals("", newCalculator.getResult());
  }

  @Test
  public void testClearOperationStartNewOperation() {
    Calculator newCalculator = calculator.input('5');

    newCalculator = newCalculator.input('5').input('2').input('+').input('3')
            .input('=').input('C');
    newCalculator = newCalculator.input('6').input('*').input('3').input('=');
    assertEquals("18", newCalculator.getResult());
  }

  @Test
  public void testOperatorAfterEquals() {
    Calculator newCalculator = calculator.input('5');

    newCalculator = newCalculator.input('-').input('2').input('=').input('+');
    assertEquals("3+", newCalculator.getResult());
  }

  @Test
  public void testOperationAfterEquals() {
    Calculator newCalculator = calculator.input('5');

    newCalculator = newCalculator.input('-').input('2').input('=').input('+').input('3').input('=');
    assertEquals("6", newCalculator.getResult());
  }

  @Test
  public void testResultInBetweenOperation() {
    Calculator newCalculator = calculator.input('5');

    assertEquals("5", newCalculator.getResult());
    newCalculator = newCalculator.input('+');
    assertEquals("5+", newCalculator.getResult());
  }

  @Test
  public void testSequenceOfOperationsAddition() {
    Calculator newCalculator = calculator.input('1');

    assertEquals("1", newCalculator.getResult());
    newCalculator = newCalculator.input('3');
    assertEquals("13", newCalculator.getResult());
    newCalculator = newCalculator.input('+');
    assertEquals("13+", newCalculator.getResult());
    newCalculator = newCalculator.input('1');
    assertEquals("13+1", newCalculator.getResult());
    newCalculator = newCalculator.input('2');
    assertEquals("13+12", newCalculator.getResult());
    newCalculator = newCalculator.input('3');
    assertEquals("13+123", newCalculator.getResult());
    newCalculator = newCalculator.input('+');
    newCalculator = newCalculator.input('1');
    assertEquals("136+1", newCalculator.getResult());
    newCalculator = newCalculator.input('3');
    assertEquals("136+13", newCalculator.getResult());
    newCalculator = newCalculator.input('0');
    assertEquals("136+130", newCalculator.getResult());
    newCalculator = newCalculator.input('+');
    assertEquals("266+", newCalculator.getResult());
    newCalculator = newCalculator.input('1');
    assertEquals("266+1", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("267", newCalculator.getResult());
  }

  @Test
  public void testSequenceOfOperationsSubtraction() {
    Calculator newCalculator = calculator.input('3');

    newCalculator = newCalculator.input('0').input('0').input('0');
    assertEquals("3000", newCalculator.getResult());
    newCalculator = newCalculator.input('-');
    assertEquals("3000-", newCalculator.getResult());
    newCalculator = newCalculator.input('1');
    assertEquals("3000-1", newCalculator.getResult());
    newCalculator = newCalculator.input('2');
    assertEquals("3000-12", newCalculator.getResult());
    newCalculator = newCalculator.input('-');
    assertEquals("2988-", newCalculator.getResult());
    newCalculator = newCalculator.input('2').input('0').input('0').input('0');
    assertEquals("2988-2000", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("988", newCalculator.getResult());
    newCalculator = newCalculator.input('-');
    assertEquals("988-", newCalculator.getResult());
    newCalculator = newCalculator.input('9').input('8').input('1');
    assertEquals("988-981", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("7", newCalculator.getResult());
  }

  @Test
  public void testSequenceOfOperationsMultiplication() {
    Calculator newCalculator = calculator.input('C').input('9');

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
    newCalculator = newCalculator.input('1');
    assertEquals("810*1", newCalculator.getResult());
    newCalculator = newCalculator.input('*');
    assertEquals("810*", newCalculator.getResult());
    newCalculator = newCalculator.input('2');
    assertEquals("810*2", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("1620", newCalculator.getResult());
  }


  @Test
  public void testSequenceOfOperationsAllOperators() {

    Calculator newCalculator = calculator.input('7');
    newCalculator = newCalculator.input('+').input('2').input('=');

    assertEquals("9", newCalculator.getResult());
    newCalculator = newCalculator.input('+');
    assertEquals("9+", newCalculator.getResult());

  }

  @Test
  public void testNegative() {
    Calculator newCalculator = calculator.input('1');
    newCalculator = newCalculator.input('+').input('1').input('+').input('1').input('=');
    assertEquals("3", newCalculator.getResult());
  }

  @Test
  public void testSubtractionIntoNegativeNumber() {
    Calculator newCalculator = calculator.input('7');

    newCalculator = newCalculator.input('-').input('8').input('=');
    assertEquals("-1", newCalculator.getResult());
  }

  @Test
  public void testAdditionSubtractionResultsInNegative() {
    Calculator newCalculator = calculator.input('1');

    newCalculator = newCalculator.input('+').input('9').input('-').input('4')
            .input('0').input('=');
    assertEquals("-30", newCalculator.getResult());
  }


  @Test
  public void testSubtractionIntoNegativeNumber2() {
    Calculator newCalculator = calculator.input('9');

    newCalculator = newCalculator.input('*').input('2').input('-').input('3')
            .input('0').input('=');
    assertEquals("-12", newCalculator.getResult());
  }


  @Test
  public void testEnteringNumberAfterEquals() {
    Calculator newCalculator = calculator.input('9');

    newCalculator = newCalculator.input('*').input('3').input('=');
    assertEquals("27", newCalculator.getResult());
    newCalculator = newCalculator.input('3');
    assertEquals("3", newCalculator.getResult());
  }

  @Test
  public void testEnteringNumberAfterEquals2() {
    Calculator newCalculator = calculator.input('7');

    newCalculator = newCalculator.input('*').input('8').input('=');
    assertEquals("56", newCalculator.getResult());
    newCalculator = newCalculator.input('3');
    assertEquals("3", newCalculator.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testResultsAtStartThrows() {
    Calculator newCalculator = calculator.input('=');
    assertEquals("", newCalculator.getResult());
  }

  @Test
  public void testResultsAtStart() {
    Calculator newCalculator = calculator;

    assertEquals("", newCalculator.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidEmptyInput() {
    Calculator newCalculator = calculator.input(' ');

    assertEquals("", newCalculator.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testEqualsFirstThrows() {
    Calculator newCalculator = calculator.input('=');

    assertEquals("=", newCalculator.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidThirdInput() {
    Calculator newCalculator = calculator.input('8');

    newCalculator = newCalculator.input('*').input('4').input('/').input('4');
    assertEquals("8", newCalculator.getResult());

  }

  @Test
  public void testBigMultiplication() {
    Calculator newCalculator = calculator.input('9');

    newCalculator = newCalculator.input('9').input('9').input('9').input('*')
            .input('9').input('9').input('9').input('9');
    assertEquals("9999*9999", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("99980001", newCalculator.getResult());
  }


  @Test
  public void testLeadingZeroWithOperation() {
    Calculator newCalculator = calculator.input('0');

    newCalculator = newCalculator.input('6').input('+').input('3');
    assertEquals("6+3", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("9", newCalculator.getResult());
  }


  @Test
  public void testLeadingZero() {
    Calculator newCalculator = calculator.input('0');

    newCalculator = newCalculator.input('2');
    assertEquals("2", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("2", newCalculator.getResult());
  }

  @Test
  public void testLeadingZero3() {
    Calculator newCalculator = calculator.input('0');

    newCalculator = newCalculator.input('0').input('0').input('0').input('0').input('3');
    assertEquals("3", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("3", newCalculator.getResult());
  }

  @Test
  public void testMultiplyNegative() {
    Calculator newCalculator = calculator.input('7');

    newCalculator = newCalculator.input('-').input('9').input('=');
    assertEquals("-2", newCalculator.getResult());
    newCalculator = newCalculator.input('*').input('2').input('=');
    assertEquals("-4", newCalculator.getResult());
  }

  @Test
  public void testNewOperatorAfterEquals() {
    Calculator newCalculator = calculator.input('7');
    newCalculator = newCalculator.input('-').input('6').input('=');
    assertEquals("1", newCalculator.getResult());
    newCalculator = newCalculator.input('-').input('6').input('+').input('2').
            input('-').input('5').input('=');
    assertEquals("-8", newCalculator.getResult());
  }

  @Test
  public void testResultAtStart() {
    assertEquals("", calculator.getResult());
  }

  @Test
  public void testNewOperandAfterEquals2() {
    Calculator newCalculator = calculator.input('9');
    newCalculator = newCalculator.input('-').input('2').input('=');
    assertEquals("7", newCalculator.getResult());
    newCalculator = newCalculator.input('3').input('6').input('=');
    assertEquals("36", newCalculator.getResult());
  }

  @Test
  public void testOperationAfterEquals2() {
    Calculator newCalculator = calculator.input('8');
    newCalculator = newCalculator.input('*').input('4').input('=');
    assertEquals("32", newCalculator.getResult());
    newCalculator = newCalculator.input('9').input('*').input('4').input('=');
    assertEquals("36", newCalculator.getResult());
  }

  @Test
  public void testSequence() {
    Calculator newCalculator = calculator.input('8');
    newCalculator = newCalculator.input('*').input('4').input('-');
    assertEquals("32-", newCalculator.getResult());
  }

  @Test
  public void testNegativeSequence() {
    Calculator newCalculator = calculator.input('8');
    newCalculator = newCalculator.input('-').input('9').input('-').input('7').input('*').input('4');
    assertEquals("-8*4", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("-32", newCalculator.getResult());
    newCalculator = newCalculator.input('-').input('9').input('+').input('7').input('=');
    assertEquals("-34", newCalculator.getResult());
  }

  @Test
  public void testResultAfterOverflow() {
    Calculator newCalculator = calculator.input('1').input('1').input('1').input('1')
            .input('1').input('1').input('1').input('1').input('1').input('1');

    assertEquals("1111111111", newCalculator.getResult());

    Exception exception = null;
    try {
      newCalculator = newCalculator.input('1');
    } catch (IllegalArgumentException e) {
      exception = e;
    }

    assertNotNull(exception);
    assertEquals("1111111111", newCalculator.getResult());
  }

  @Test
  public void testAddingZero() {
    Calculator newCalculator = calculator.input('0').input('+').input('0');
    assertEquals("0+0", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("0", newCalculator.getResult());
  }

  @Test
  public void testSubtractingZero() {
    Calculator newCalculator = calculator.input('0').input('-').input('0');
    assertEquals("0-0", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("0", newCalculator.getResult());
  }

  @Test
  public void testMultiplyingZero() {
    Calculator newCalculator = calculator.input('0').input('*').input('0');
    assertEquals("0*0", newCalculator.getResult());
    newCalculator = newCalculator.input('=');
    assertEquals("0", newCalculator.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidClear() {
    Calculator newCalculator = calculator.input('0').input('c');
    assertEquals("", newCalculator.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLowestPossibleNumberThrows() {
    Calculator newCalculator = calculator.input('-').input('1').input('1')
            .input('1').input('1').input('1').input('1')
            .input('1').input('1').input('1').input('1')
            .input('1').input('1').input('1').input('1').input('=');
    assertEquals("-11111111111111", newCalculator.getResult());
  }

  @Test
  public void testLowestPossibleNumber() {
    Calculator newCalculator = calculator.input('1').input('-').input('1').input('1')
            .input('1').input('1').input('1').input('1')
            .input('1').input('1').input('1').input('1').input('=');
    assertEquals("-1111111110", newCalculator.getResult());
  }

}
