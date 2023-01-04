package com.company;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/* When we execute test methods in a test class by default for each test method, junit will create a new instance of a test class(that provides a clean separation of state
   between tests)(refer img_1). And the execution order of these methods by default is not obvious, the best practice in most cases is to think that this methods run in a
   random order. now junit API also provides us with a few lifecycle methods. These methods help us manage our code within junit class and execute the code at specific phase
   in test lifecycle.
 * If test class has a method that is not annotated with any annotation, then it is just a regular Java method, and it does not have to do anything with junit test API. but
   test methods that invoke method under test and assert results, they are annotated with test annotation.
 * lifecycle methods are annotated with different annotations(refer img_2).

        - @BeforeAll  : a method that is annotated with before all annotation will be executed before all test methods in the test class. And we will use this method as a
                        setup method, to prepare needed resources before any test methods can execute.
                        ex: if we're working with a database, then this method can be used to create a database.
        - @AfterAll   : a method that is annotated with this annotation will be executed after all test methods at the very end. So you can use this method for cleanup
                        purposes, for example, to clean up any resources that were needed for test methods to execute.
                        ex: we can use this method to delete database that was created in setup method.
        - @BeforeEach : a method that is annotated with this annotation will execute before each unit test methods. we will use this method for setup purposes. If we have
                        code that needs to be executed before each test method, we can put that code in here. we can use this method to initialize objects or reset data to
                        make sure that each test method works with objects that have a new and clean state.
                        ex: For example, if all of our test methods need to create the same object, then we will move this object creation to a before each method. This way
                        we do not duplicate code in each of our test methods over and over again.
        - @AfterEach  : a method that is annotated with this annotation will be executed after each unit test method. And we will use this method for cleanup purposes,
                        ex: for example, to close database connection or cleanup resources that was needed for test method to execute.
                        If we're running integration test for example, and our test method made some changes in the database, then we can use this method to delete those records.

  * methods that are annotated with @BeforeAll or @AfterAll annotations, needs to be static.                                                                                 */

@DisplayName("Test Math operations in com.company.Calculator class") class CalculatorTest {

    Calculator calculator;

    @BeforeAll static void setup() {
        System.out.println("executing @BeforeAll method");
    }

    @AfterAll static void cleanUp() {
        System.out.println("executing @AfterAll method");
    }

    @BeforeEach void beforeEachTestMethod() {
        calculator = new Calculator();
        System.out.println("executing @BeforeEach method");
    }

    @AfterEach void afterEachTestMethod() {
        System.out.println("executing @AfterEach method");
    }

    @DisplayName("test 8/2=4") @Test void testIntegerDivision_WhenEightDivideByTwo_ShouldReturnFour() {
        System.out.println("Running test 8/2=4");
        int actualResult = calculator.integerDivision(8, 2);
        assertEquals(4, actualResult, () -> "8/2 should be 4");
    }

    @DisplayName("test 35-4=31") @Test void testIntegerSubtraction_WhenFourSubtractFromThirtyFive_ShouldReturnThirtyOne() {
        System.out.println("Running test 35-4=31");
        int actualResult = calculator.integerSubtraction(35, 4);
        assertEquals(31, actualResult, () -> "35-4 should be 31");
    }

}