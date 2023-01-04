package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/* parameterized unit tests: How to write unit tests that accept (multiple variations of) input parameters.
   Notice that most of the unit tests that we have written so far, they do not accept any input parameters. This is because we do not really invoke unit tests from our own code
   as we do it with regular Java methods. And we do not really pass input parameters to junit test methods as we do it with regular java methods. But junit does provide us with a
   way to invoke test methods and make it accept input parameters using parameterized tests.
   advantage: using parameterized tests we can make our unit test run with more input parameter variations rather than run one time with defined values inside the test method just
   like before.
 * And to do that:
   @ParameterizedTest: we will need to change our @Test annotation to be @ParameterizedTest. It is still going to be a test method, but now it can accept parameters.
   @MethodSource(""): and to provide multiple different variations of input parameters we'll need to add another annotation called MethodSource. And in the double quotes provide
   a 'name of the method' that will be used to provide (different variations of) input arguments. that method will need to be static, and it will need to return a stream of
   arguments.   */

@DisplayName("Test Math operations in Calculator class") class CalculatorTest {

    Calculator calculator;

    @BeforeEach void setup() {
        calculator = new Calculator();
    }

    // non-parameterized test: will run only one time with a single pair of values.
    @DisplayName("test 3*5=15") @Test void integerMultiplication_WhenThreeMultiplyByFive_ShouldReturnFifteen() {
        System.out.println("Running test 3*5=15");
        int multiplicand = 3;
        int multiplier = 5;
        int expectedResult = 15;
        /* We have defined multiplicand and multiplier inside this method as a local variables. And then we use these local variables as input parameters into our method under test
        which is 'integerMultiplication' (we defined those values inside the test method, not received as test method arguments). But we can make our method under test run with more
        input parameter variations than this two. And to do that we need to accept those multiple values as test method's input arguments.                                          */
        int actualResult = calculator.integerMultiplication(multiplicand, multiplier);
        //int actualResult = calculator.integerMultiplication(3, 5);
        assertEquals(expectedResult, actualResult, () -> multiplicand + " * " + multiplier + " should be " + expectedResult);
    }

    //to be able to compare expected result with the actual result, We also wanted to accept the expected result value along with test values as parameters.
    @ParameterizedTest @MethodSource("integerDivisionInputParameters") @DisplayName("test integerDivision [dividend,divisor,expectedResult]") void testIntegerDivision(int dividend,
        int divisor, int expectedResult) {
        System.out.println("Running test " + dividend + "/" + divisor + "=" + expectedResult);
        int actualResult = calculator.integerDivision(dividend, divisor);
        assertEquals(expectedResult, actualResult, () -> dividend + " / " + divisor + " should produce " + expectedResult);
    }

    /* In the MethodSource annotation we have included the name of the method that is being used as a source of input parameters. If the name of this method is exactly the same as
    a test method name, junit allows us to remove this name from a method source annotation. */
    @ParameterizedTest @MethodSource @DisplayName("test integerSubtraction [minuend,subtrahend,expectedResult]") void testIntegerSubtraction(int minuend, int subtrahend,
        int expectedResult) {
        System.out.println("Running test " + minuend + "-" + subtrahend + "=" + expectedResult);
        int actualResult = calculator.integerSubtraction(minuend, subtrahend);
        assertEquals(expectedResult, actualResult, () -> minuend + " - " + subtrahend + " should produce " + expectedResult);
    }

    //these methods will need to be static, and return a stream of arguments & and it would need to be called just as we named it in @MethodSource annotation.
    private static Stream<Arguments> integerDivisionInputParameters() {
        //So now if we run the test method that fetch multiple arguments from this method, it'll run 3 times(depending on the arguments provided) each time with a different set of arguments.
        return Stream.of(Arguments.of(25, 5, 5), Arguments.of(80, 4, 20), Arguments.of(28, 4, 7));
    }

    private static Stream<Arguments> testIntegerSubtraction() {
        return Stream.of(Arguments.of(35, 4, 31), Arguments.of(55, 25, 30), Arguments.of(8, 5, 3));
    }

}

/* note: we've used System.out.println in our unit test method, but this is just for demonstration purposes. Usually we do not print anything in the test method, or if we do print
 something then it should be temporary, and we should not rely on the information that we print using System.out.println in our test method.  */