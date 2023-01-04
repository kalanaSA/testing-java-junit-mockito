package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.*;

/* there is another annotation that we can use to supply parameters to our test method, that is called @ValueSource().
 * @ValueSource(ints = { , , }) :  value source annotation is very simple, and it lets us supply *only one single array of values. So unit test method can accept only
   single parameter.
 * @ValueSource work with different other data types. (ex: ints, strings, longs, doubles, chars etc.). in here, we've used data type of parameters as strings.                    */

@DisplayName("Test Math operations in Calculator class") class CalculatorTest {

    Calculator calculator;

    @BeforeEach void setup() {
        calculator = new Calculator();
    }

    // this unit test will run 3 times, Each time it will accept one parameter as a first name.
    @ParameterizedTest @ValueSource(strings = { "Kalana", "Ashani", "Limesha" }) void valueSourceDemonstration(String firstName) {
        System.out.println(firstName); //demonstrate purpose only
        assertNotNull(firstName);
    }

    @ParameterizedTest @CsvFileSource(resources = "/integerDivision.csv") @DisplayName("test integerDivision [dividend,divisor,expectedResult]") void testIntegerDivision(
        int dividend, int divisor, int expectedResult) {
        System.out.println("Running test " + dividend + "/" + divisor + "=" + expectedResult);
        int actualResult = calculator.integerDivision(dividend, divisor);
        assertEquals(expectedResult, actualResult, () -> dividend + " / " + divisor + " should produce " + expectedResult);
    }

}
