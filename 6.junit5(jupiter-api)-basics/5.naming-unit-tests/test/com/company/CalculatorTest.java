package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    /* When it comes to naming the unit test methods, then technically we can give our unit test method any name we like. But as we work with different teams and developers,
       we will notice that many developers follow a specific naming convention to help them make test methods more descriptive. It is good to make the name of the test method
       descriptive and speak for itself. And the goal is to understand what the test method is going to test just by looking at the method name rather than by looking at the
       code it contains and try to figure it out from the code.
     * it's obvious that what a test method is doing by looking at its code when it's simple. but when we're reviewing test methods written by someone else and there is a lot
       more test methods in the class and there is much more code in the test method itself, naming of test method will be very helpful.
     * disadvantages of this approach is name of test methods look very long in the test report. using @DisplayName annotation can make these names look shorter and less technical.                                          */

    //specific pattern for naming test method(template we're going to follow)
    // test< system under test >_< condition or state change >_< expected result>
    @Test void testIntegerDivision_WhenFourIsDividedByTwo_ShouldReturnTwo() {   //testIntegerDivision_WhenDivisorIsValidInteger_ShouldReturnTwo
        Calculator calculator = new Calculator();
        int actualResult = calculator.integerDivision(8, 2);
        assertEquals(4, actualResult, () -> "8/2 should be 4");
    }

    @Test void testIntegerDivision_WhenDividendDividedByZero_ShouldThrowArithmeticException() {
        
    }

}