package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    /* If the assertion fails the test, then the unit test provide its own error message. But if the default error message is not descriptive enough, then we can include
       additional parameters into our own optional custom error message. For example, we can include input parameters and expected result into the error message and to make
       it more descriptive. So error message in the test report will have additional information for the person who reads this test report.
     * In this particular test method, it might not look very helpful, but when there are other test methods in our project, include this additional information into
       error message will be very helpful.
     * if we have many test methods and each test method has assertions with kind of dynamically computed message, this might slow down our test methods a little. in a
       small application we will not even notice the difference, but in a much larger application With many test methods, an assertion message is used, Developers like
       to optimize it. and the reason it might slow down our tests a little is because this message will get computed every time we run test methods whether it passes
       or fails, this message will always be computed. It gets executed even though it might never be used. So to optimize performance of we need tests. Developers like
       to convert this test message into a lambda. Now this message is a lambda function that will be executed only when this assertion fails the test method. Otherwise,
       if the test is passing, this lambda function will never get executed and no resources will be spent to compute this error message.
     * So again, using this message parameter is optional. we will see many test methods that do not use it at all. But generally it is considered a good practice to provide a
       short but descriptive error message to help the person who's reading this test report easier understand the reason why this test method has failed.
       by converting into a lambda, we know how to make the description error message execute only when it is actually needed.                                              */
    @Test void testIntegerSubtraction() {
        Calculator calculator = new Calculator();
        int minuend = 35;
        int subtrahend = 4;
        int expectedResult = 32;
        int ActualResult = calculator.integerSubtraction(minuend, subtrahend);
        //assertEquals(expectedResult, ActualResult, "35 - 4 did not produce 32");
        //assertEquals(expectedResult, ActualResult, minuend + " - " + subtrahend + " did not produce " + expectedResult);
        assertEquals(expectedResult, ActualResult, () -> minuend + " - " + subtrahend + " did not produce " + expectedResult);
    }
}