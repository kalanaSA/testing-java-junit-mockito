package com.company;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/* There is away/pattern to structure(organize) our code inside a test method. And if we follow this pattern consistently, then it will be easier to understand our test methods.
   So this pattern is widely used by many developers, and it is known by two different names.
        - AAA (Arrange | Act | Assert)
        - GWT (Given | When | Then)     : same pattern, but known under a different name.
          Given that we have this information(these are preconditions),
          When we use this information to invoke our method under test and let it act on it,
          Then the following should be true.                                                                                                                                   */

@DisplayName("Test Math operations in com.company.Calculator class") class CalculatorTest {

    @DisplayName("test 8/2=4") @Test void testInteger_DivisionWhenEightDivideByTwo_ShouldReturnFour() {
        //Arrange (arrange is where we prepare and initialize all the needed variables and objects that are needed by our method under to work and return expected result.
        Calculator calculator = new Calculator(); //to invoke method under test(integerDivision), first need to create an instance of Calculator class.
        /* we can use arrange section to prepare input parameters that either Calculator class or method under test accept. in here we can pass these input parameters into
           method under test right away. But if input parameters are complex object, then it is better to prepare them in the arrange section. */
        int dividend = 8;
        int divisor = 2;
        int expectedResult = 4;

        //Act (in this section we will actually invoke the methods we are testing(method under test))
        int actualResult = calculator.integerDivision(8, 2);

        //Assert (will be used to validate the return value which we have received from our method under test)
        assertEquals(expectedResult, actualResult, () -> "8/2 should be 4");  //If the returned value is what is expected and is correct, then test method will pass.
    }

    @DisplayName("test 35-4=31") @Test void testIntegerSubtraction_WhenFourSubtractFromThirtyFive_ShouldReturnThirtyOne() {
        //Arrange | Given
        Calculator calculator = new Calculator();

        //Act | When
        int actualResult = calculator.integerSubtraction(35, 4);

        //Assert | Then
        assertEquals(31, actualResult, () -> "35-4 should be 31");
    }

}

//so a very clear and descriptive test method name together with displayName annotation and arrange act and assert pattern help us easier understand what the test method is trying to test.