package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

/* So instead of providing a very long list of parameter values in @CsvSource() annotation, we can use a different annotation called @CsvFileSource() that use csv file resource.
 * @CsvFileSource(resources = "/integerDivision.csv") : in the brackets, I can provide a path to resource file.
 * storing parameter values in a separate file can be very helpful when we need to test our method with a very long list of different values.
   ex: if we have a method that validates names and email addresses, then we can create a separate CSV file with that very long list of different names and email addresses.
   And then we can test our method under test with all sorts of different kinds of values.
 * way to create file in test resources' folder(using intellij IDEA):
        - create a package inside the 'test'(test sources root) folder. I've named it 'resources' in here.
        - right mouse click project -> open module settings -> mark that directory as 'Test Resources' root folder. (refer img_1)
        - create a new file inside the Test resource folder(which is resources in here)
        - give that file a name exactly the same name we've used in @CsvFileSource annotation.
        - add comma separate values in to the file. (take a new line for new set of values)                                                                                     */

@DisplayName("Test Math operations in Calculator class") class CalculatorTest {

    Calculator calculator;

    @BeforeEach void setup() {
        calculator = new Calculator();
    }

    // unit test method will run 3 times with the parameter values that we have provided in 'integerDivision.csv' file.
    @ParameterizedTest @CsvFileSource(resources = "/integerDivision.csv") @DisplayName("test integerDivision [dividend,divisor,expectedResult]") void testIntegerDivision(
        int dividend, int divisor, int expectedResult) {
        System.out.println("Running test " + dividend + "/" + divisor + "=" + expectedResult);
        int actualResult = calculator.integerDivision(dividend, divisor);
        assertEquals(expectedResult, actualResult, () -> dividend + " / " + divisor + " should produce " + expectedResult);
    }

}
