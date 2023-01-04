package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/* JUnit provides us with another very useful annotation that can be used to supply method arguments to a test method, and it is called CSV source.
 * instead of @MethodSource, we will use @CsvSource here. because we use csv source now and we no longer use method source, the method or methods that we have added as source
   to the test method before is not needed anymore.
 * @CsvSource({,}) : allows us to supply a list of arguments as comma separated values.                                                                                          */

@DisplayName("Test Math operations in Calculator class") class CalculatorTest {

    Calculator calculator;

    @BeforeEach void setup() {
        calculator = new Calculator();
    }

    //The same test method run 3 times and each time with a different set of arguments.
    @ParameterizedTest @CsvSource({ "20,4,5", "60,12,5", "24,4,6" }) @DisplayName("test integerDivision [dividend,divisor,expectedResult]") void testIntegerDivision(int dividend,
        int divisor, int expectedResult) {
        System.out.println("Running test " + dividend + "/" + divisor + "=" + expectedResult);
        int actualResult = calculator.integerDivision(dividend, divisor);
        assertEquals(expectedResult, actualResult, () -> dividend + " / " + divisor + " should produce " + expectedResult);
    }

    @ParameterizedTest @CsvSource({ "35,4,31", "25,6,19", "8,5,3" }) @DisplayName("test integerSubtraction [minuend,subtrahend,expectedResult]") void testIntegerSubtraction(
        int minuend, int subtrahend, int expectedResult) {
        System.out.println("Running test " + minuend + "-" + subtrahend + "=" + expectedResult);
        int actualResult = calculator.integerSubtraction(minuend, subtrahend);
        assertEquals(expectedResult, actualResult, () -> minuend + " - " + subtrahend + " should produce " + expectedResult);
    }

}

/*
    @CsvSource({ "apple,orange",
             "apple,''",  -empty string
             "apple," })  -equal to null
*/