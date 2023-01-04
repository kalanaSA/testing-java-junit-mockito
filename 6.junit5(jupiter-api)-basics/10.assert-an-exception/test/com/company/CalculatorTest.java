package com.company;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

// How to verify the method under test throws correct the exception or not. to do that we use assertThrows assertion.

@DisplayName("Test Math operations in Calculator class") class CalculatorTest {

    Calculator calculator;

    @BeforeEach void beforeEachTestMethod() {
        calculator = new Calculator();
    }

    @DisplayName("division by zero") @Test void testIntegerDivision_WhenDividendIsDivideByZero_ShouldReturnArithmeticException() {
        //Arrange
        int dividend = 4;
        int divisor = 0; //int divisor = 2;
        String expectedExceptionMessage = "/ by zero"; //When the arithmetic exception takes place, the default error message looks usually like this.

        //Act & Assert (assert that if integerDivision method is invoked with these two input parameters and arithmetic exception will be thrown)
        //assertThrows(expectedExceptionType, executable, optionalMsg) & will return Throwable.
        ArithmeticException actualException = assertThrows(ArithmeticException.class, () -> {
            //Act
            calculator.integerDivision(dividend, divisor);
        }, "division by zero should have thrown an arithmetic exception");

        //Assert (check with the exception message that was returned is what we're expecting)
        assertEquals(expectedExceptionMessage, actualException.getMessage(), () -> "unexpected exception message");
    }

}