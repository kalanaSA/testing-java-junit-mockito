package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    //passing unit test
    @Test void testIntegerDivision() {
        Calculator calculator = new Calculator();
        int result = calculator.integerDivision(4, 2);
        assertEquals(2, result); /* if the result is not going to be equal to 2, this assertion will fail and the unit test will also fail.
        If the both two values are equal to 2, then this assertion will get successful and unit test will pass. */
    }

    /* failing unit test: here we're testing 'integerMultiplication' method(method under test which is multiply two numbers) work as expected or not. and if it's not then this
       test will fail. so we realize that there is issue with our 'integerMultiplication' method's business logic and should correct until this test method(unit test) pass.
       that's why writing unit tests first is more important. */
    @Test void testIntegerMultiplication() {
        Calculator calculator = new Calculator();
        int result = calculator.integerMultiplication(3, 3);
        assertEquals(9, result);
    }
}

/* motivation : the business logic inside a method that we are testing could be very complex, it can have many lines of code, it can have a very complex business logic containing
   encryption and decryption algorithms. but we should not be afraid of the business logic inside the method that we are testing seems to be very complex. that does not matter
   much when we are writing a unit test method. even though the business logic inside of method under test can be very complex, our unit tests can be very small.
   so as a person who writes unit test method, all we need to do is to verify that for a given input parameters, the method under test returns expected result and that's it.
   we do not really need to understand all the details of that complex business logic you are testing or be able to write it yourself. we just need to verify that for a given
   input parameters, it returns an expected result.
   when we realized this very important detail, level of our confidence will be doubled. we become very confident that we can write unit tests for any project, and it does not
   matter much how complex that business logic is. it gives us some confidence that writing unit tests is not very difficult, but there is more to learn of course. there are
   other use cases that we need to consider.                                                                                                                                  */