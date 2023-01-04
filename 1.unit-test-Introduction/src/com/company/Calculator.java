package com.company;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Calculator {

    public int integerDivision(int dividend, int divisor) {
        return dividend / divisor;
    }

    @Test void testIntegerDivision_whenValidValuesProvided_shouldReturnExpectedResult() {
        //test method is also divided into 3 main sections

        //Arrange : create a new instance of Calculator class
        Calculator calculator = new Calculator();  //

        //Act : this is the section we invoke integerDivision method(method under test) and provided with two valid parameters and captured the result. want to make sure that this
        //method works as expected, and it performs integer division correctly.
        int result = calculator.integerDivision(4, 2); //

        //Assert :  this is a section where we use special junit methods(assert methods) to verify that the result is correct. by comparing expecting values and actual result that
        // was returned from method under test(integerDivision). third parameter is an optional hint error message that will be printed in the console if this test method fails.
        Assertions.assertEquals(2, result, "4/2 should have returned 2");
    }

}
