package com.company;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

/* changing test instance life cycle:
      - By default  junit creates a new instance of test class for each test methods. So if we have 3 test methods in a single test class, then when we run all the test
        methods in that test class, for each test method junit will create a new instance of a test class. In this case, test methods are completely independent from each other,
        and they can not share state. we cannot share information between these test methods and the changes made to instance state by one test method are not available in
        another test method. This is default behavior, and it helps junit to run unit test in isolation from each other.(refer img)
 * this default behavior can be changed and if needed we can configure junit to run test methods in the same test instance.
 * @TestInstance(TestInstance.Lifecycle.PER_CLASS) : to execute all test methods on the same test instance. this indicates that instead of creating a new class instance per method,
   we want the new test instance per class. When using this mode, only one test instance will be created per test class.
   By sharing a single instance of a test class allows us to make test methods depend on each other and share information between each other.
 * Advantage:
      - this is very helpful when working with INTEGRATION TESTS. because now our test methods can share state stored in object instance variables.
        we've learned how to configure our test methods to run in order one after another. Running ordered test methods in a single class instance and being able to share
        instance variables between those test methods gives us even more control when writing test methods that depend on each other.                                                        */

//@TestInstance(TestInstance.Lifecycle.PER_METHOD) //default configuration
@TestInstance(TestInstance.Lifecycle.PER_CLASS) public class InstanceLifeCycleTest {

    @AfterEach void afterEach() {
        System.out.println("The state of instance object is " + completed);
    }

    //instance variable: to see if there is indeed single instance of a class created for each test method.
    StringBuilder completed = new StringBuilder("");

    @Test void testA() {
        System.out.println("running test A");
        completed.append("1");
    }

    @Test void testB() {
        System.out.println("running test B");
        completed.append("2");
    }

    @Test void testC() {
        System.out.println("running test C");
        completed.append("3");
    }

    @Test void testD() {
        System.out.println("running test D");
        completed.append("4");
    }

}
