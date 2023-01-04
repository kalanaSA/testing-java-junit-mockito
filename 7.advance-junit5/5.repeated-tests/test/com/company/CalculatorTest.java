package com.company;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

/* Test methods that are annotated,
        @Test: These are regular test methods and one executed they run one time only.
        @ParameterizedTest: They accept method arguments, and they can execute multiple times depending on how many sets of arguments we provide.
        @RepeatedTest(3): allows us to repeat the same test method multiple times(specific number of times) to make sure that our method under test still behaves consistently
        and for provided input parameters still works as expected, even if we run it multiple times.

 * To run repeatedly regular test method multiple times, we will need to remove @Test annotation and use @RepeatedTest() annotation instead. in the brackets of @RepeatedTest(5)
   annotation, we will need to provide the number of times that we want test method to repeat.
 * each invocation of repeated test method behaves exactly like the execution of a regular test. This means that for each repetition, the lifecycle methods like for example,
   before each or after each will also be executed.                                                                                                                         */

@DisplayName("Test Math operations in Calculator class") class CalculatorTest {

    Calculator calculator;

    @BeforeEach void setup() {
        System.out.println("executing @BeforeEach method");
        calculator = new Calculator();
    }

    @RepeatedTest(3) @DisplayName("test 8/2=4") void testIntegerDivision_WhenEightDivideByTwo_ShouldReturnFour() {
        System.out.println("Running division by zero");
        int actualResult = calculator.integerDivision(8, 2);
        assertEquals(4, actualResult, () -> "8/2 should be 4");
    }

    /* if needed, we can also get access to repetition information inside of our test method. And to do that junit allows us to inject 'RepetitionInfo' object as a method
       argument to our test method. additionally to injecting the repetition information to our test method, we can also inject 'TestInfo' object and get information
       about the currently running test.
     * we can also use @RepeatedTest annotation to change the display name of each repetition in the test report. to provide custom names we can use @RepeatedTest annotation's
       property called 'name'. we can actually inject repetition information right into display name using template variables. ex: {currentRepetition}                       */
    @RepeatedTest(value = 3, name = "{displayName}. Repetition {currentRepetition} of {totalRepetitions}") @DisplayName("division by zero") void testIntegerDivision_WhenDividendDivideByZero_ShouldReturnArithmeticException(
        RepetitionInfo repetitionInfo, TestInfo testInfo) {
        System.out.println("Running " + testInfo.getTestMethod().get().getName());
        System.out.println("Repetition #" + repetitionInfo.getCurrentRepetition() + " of " + repetitionInfo.getTotalRepetitions());

        int dividend = 4;
        int divisor = 0;
        String expectedExceptionMessage = "/ by zero";
        ArithmeticException actualException = assertThrows(ArithmeticException.class, () -> {
            calculator.integerDivision(dividend, divisor);
        }, "division by zero should have thrown an arithmetic exception");
        assertEquals(expectedExceptionMessage, actualException.getMessage(), () -> "unexpected exception message");
    }

    @ParameterizedTest @CsvSource({ "35,4,31", "25,6,19", "8,5,3" }) @DisplayName("test integerSubtraction [minuend,subtrahend,expectedResult]") void testIntegerSubtraction(
        int minuend, int subtrahend, int expectedResult) {
        System.out.println("Running test " + minuend + "-" + subtrahend + "=" + expectedResult);
        int actualResult = calculator.integerSubtraction(minuend, subtrahend);
        assertEquals(expectedResult, actualResult, () -> minuend + " - " + subtrahend + " should produce " + expectedResult);
    }

}
