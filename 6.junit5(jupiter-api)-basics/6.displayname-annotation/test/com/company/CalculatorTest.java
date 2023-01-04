package com.company;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*  @DisplayName annotation very helpful in making our test names easy to understand and also less technical. when we run the tests, name of the test methods appears in the test
    report exactly as we have named it in the test class. And it looks very technical. So if needed, we can make this test method names and also the name of the test class look
    less technical and more friendly in the test report by using DisplayName annotation. doing that we can see(in the test report) a shorter and less technical test names that
    much easier to read and understand what exactly we are doing instead of very long test names.                                                                                    */

@DisplayName("Test Math operations in Calculator class") class CalculatorTest {

    @DisplayName("test 8/2=4") @Test void testIntegerDivision_WhenEightDivideByTwo_ShouldReturnFour() {
        Calculator calculator = new Calculator();
        int actualResult = calculator.integerDivision(8, 2);
        assertEquals(4, actualResult, () -> "8/2 should be 4");
    }

    @DisplayName("division by zero") @Test void testIntegerDivision_WhenDividendDividedByZero_ShouldThrowArithmeticException() {
        fail("not implemented");
    }

    @DisplayName("test 35-4=31") @Test void testIntegerSubtraction_WhenFourSubtractFromThirtyFive_ShouldReturnThirtyOne() {
        Calculator calculator = new Calculator();
        int actualResult = calculator.integerSubtraction(35, 4);
        assertEquals(31, actualResult, () -> "35-4 should be 31");
    }

}