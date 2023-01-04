package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    @Test void testIntegerDivision() {
        Calculator calculator = new Calculator();
        int result = calculator.integerDivision(4, 2); //invoke method under test
        assertEquals(2, result, "4/2 should be 2");
    }

}

/*  # Assertions

    * assertions are actually what we use to verify if the method under test that we are testing works well and returns correct result(validate the result that our method under
      test has returned and make a decision whether this unit test should pass or fail). Each assertion is a small utility method that makes a decision whether this unit test
      should pass or fail. And it makes a decision by evaluating condition with a given input parameters. If condition is not true, then assertion fails, and it throws an
      exception with a message.
      These assertions are actually static methods that come from Jupiter API package's assertion class, and we have imported them using the import statement here.
           - import static org.junit.jupiter.api.Assertions.*; : have used astrix to import all assertions from the assertions class.

    * Assertion message : To customize the error message, we can include our own custom error message into assertion as a one additional parameter that assertion accept called
      message, and it's an optional and not mandatory to provide any message there. But it is considered a good practice to always provide a good and descriptive message for
      each assertion. This message will then be printed in the report, but only if this assertion fails the test method. If test method passes, then of course there will be
      no message printed. But if it fails, then we can use this additional message to include possible explanation. in this case, when test report is reviewed by another developers
      on our team, They will have more information to understand why this particular assertion has failed the test.

    * Other Assertions : There are more assertions(assert methods including overload ones) that accepts different types of input parameters.

            - fail()            - unconditional fail assertion. It unconditionally or intentionally fails test method without checking for any conditions.
            - assertNotEquals() - help us verify if the two provided parameter values are not equal. If they are equal, then this test method will fail because this
                                   assertion will throw an exception.
            - assertTrue()      - help us validate if the provided value is true or false. If that is true, then the unit test will pass. Otherwise, this assertion will throw an
                                   exception and unit test will fail.
            - assertNotNull()   - validate if the object that method under test has returned is not null.
            - assertThrows()    - helps us to check if the method under test has thrown an expected exception. Or if we want to validate that our method does not throw an
                                   exception, then we will use assertDoesNotThrow().
            - assertFalse()
            - assertNull()                                                                                                                                                       */