package com.company;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/* Usually it's not a good idea to disable unit test, and if unit is failing for some reason, and you're not sure why it is why we do not disable it, trying to
   figure out why it is failing and fix it.
 * But if you do need to disable your units, there are a couple of ways to do it.

        - remove or comment out @Test annotation : If there is no test annotation, then it is not a test method, and it will not be executed when we run all our
          unit tests, and it will not be included in a test report also.
        - @Disabled Annotation : this will be a better way to disable our test because the test won't run, but it will be included in a test report.
          if we look at the test report, then we clearly see that there is a test method that is disabled.
          so test is visible, and it is better because we can see it and marked as disabled and reminds us that we do have a test method that we still need to work on.

 * think if we're working on a team with other developers, and we need to commit our code but the build is failing because of a failing of a unit test method. do not
   disable that unit test method just to make our code go through the build stage. that unit test might be failing for a reason, and it is better to figure out that
   reason rather than disabling the unit test.                                                                                                                       */
@DisplayName("Test Math operations in com.company.Calculator class") class CalculatorTest {

    Calculator calculator;

    @BeforeEach void beforeEachTestMethod() {
        calculator = new Calculator();
    }

    @Disabled("TODO: still need to work on it") @DisplayName("test 8/2=4") @Test void testIntegerDivision_WhenEightDivideByTwo_ShouldReturnFour() {
        int actualResult = calculator.integerDivision(8, 2);
        assertEquals(4, actualResult, () -> "8/2 should be 4");
    }

    @DisplayName("test 35-4=31") @Test void testIntegerSubtraction_WhenFourSubtractFromThirtyFive_ShouldReturnThirtyOne() {
        int actualResult = calculator.integerSubtraction(35, 4);
        assertEquals(31, actualResult, () -> "35-4 should be 31");
    }

}