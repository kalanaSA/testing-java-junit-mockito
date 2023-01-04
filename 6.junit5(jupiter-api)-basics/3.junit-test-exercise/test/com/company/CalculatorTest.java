package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test void testIntegerDivision() {
        Calculator calculator = new Calculator();
        int result = calculator.integerDivision(4, 2);
        assertEquals(2, result, "4/2 should be 2");
    }

    /* when writing unit tests, we should not really worry how complex the business logic is inside of method under test. Method under test can have long and complex
       code, but we should not worry much. All we care about is that method that we're testing returns an expected result for provided input parameters.
     * when testing a method under test, we should test it with both valid and invalid input parameters. We should think of all the possible input values that can make our method
       under test break or produce unexpected results. for example will it still work?
        - if we give it values that are too large or values that are too small?
        - If input parameters are strings. Then will it work if the string value is too short or if it is too long?
        - If the input parameter is an object, then method under test still work If we give it null instead of a real object?
       for each different cases that can potentially make method under test break or return unexpected result, we should create a separate unit test methods.
       that means we can have several test methods, each testing the same method under test, but with different input parameters.                                                */
    @Test void testIntegerSubtraction() {
        Calculator calculator = new Calculator(); //instance of Calculator class
        int result = calculator.integerSubtraction(35, 4); //invoke integerSubtraction method using calculator object
        assertEquals(31, result, "35-4 should be 31");
    }

}