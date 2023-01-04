package com.company;

/*
# Testing java code with JUnit & Mockito : we write unit tests to make sure that application we have created work as expect/promised(increase quality of a java application).
    1 Introduction to Unit testing
    2 Junit 5 (basics of JUnit 5 API)
    3 Mockito Framework (knowing how to use JUnit together with Mockito, we will be able to test even more complex java code)
    4 TDD (how to write java code following test driven development principles)
    5 write java code and unit tests using integrated development environments:
        - IntelliJ IDEA
    6 Java projects can be different and there is different ways to create,build and run java projects. So ways we can configure Junit support in different types of java projects
      and different development environment will also differ.
        - Regular Java project
        - Maven
        - Gradle

# what is a unit test:
    * A unit test is a very small, self-contained java method that we write to test some/individual part of our code. To make sure part of our code(a method) work as expect we will
      need to write one or more unit tests. It is a small piece of code that we write to invoke the method that we are testing, and it will validate that it has produced an expected
      result.
    * To test application code, we usually write more than one unit tests. One unit test will test our method with valid parameters, while another unit test will intentionally
      provide a method with invalid parameter values. And in both scenarios, our unit test methods will validate if the method we are testing produces expected result.
      The method under test may contain a very complex business logic but the code in unit tests are very simple(because all it does is it calls our method under test with
      specific input parameters and validates the returns result), usually very short and does not contain much of complex business logics.(refer image_1)
    * important to keep in mind that unit test should be testing one particular functionality only. We should not try to make one single unit test method that tests multiple methods
      of our class at the same time.
    * So to write unit tests we will use JUnit. JUnit provides us with an API to write small code snippets to test our Java methods.
    * normally our java application will contain many classes, and each class may contain several methods. so to ensure that our application is well tested. we want to write
      more than one unit tests for most of the methods in our class. (refer image_2)
    * if a unit test does not receive expected result, it fails, and then it will be marked with red color. if not it passed and will be marked with green color.
    * units tests are very small, and they run very fast. They run very fast because when testing a method, any dependencies that method might have are replaced with mock
      objects.
      ex: For example, if the method we're testing sends a https request, and depends on a http client object a real http client object will need to be replaced with a fake or mock
          version of it. and there can be different kinds of these fake objects, that can be a fake object, that can be mock object, or they can be a spy object. So real http
          object will be replaced with mock version of it and no real http request will be sent. And this will make our unit test method run much faster.

    * And we do it because unit test Method is not actually testing how http client works. unit test is focused only on testing java code inside the method that we are testing.
      If the method we are testing depends on another object, then dependency will need to be replaced with mock objects. And when all external dependencies are replaced with mock
      objects, with predefined behavior or with hard coded values, then the method we are testing will work very fast and our unit test will also work very fast. And this is why, if
      needed we can write more than one unit test to test a single method.
           ex: For example, one unit test will be to test method with valid input parameter values.
               Another unit test will be to test method with invalid input parameter values.
               And another unit test can be to test method with invalid response from a http client.
               we can write different unit tests to make sure that the method we are testing works well and reliable under different conditions and always produces expected result.

# Why write unit tests:

    * It's very quickly to run our application and just do manual testing to make sure that things work. So why write code(unit tests) to make sure that another code works? Why not
      test manually?
    * Advantage of writing unit tests to test our code.
        - Make sure that the code works :
        - Code works with different(valid and invalid) input parameters : we can do manual testing and manually provide valid and invalid input parameters. But if we have a very
          large application with many features, then it is very easy to forget to test one of the features with one of invalid parameters. especially when we have many developers
          working on the project, when changes to our application are made on a daily basis, and when we need to do frequent tests. manual testing or application that has many
          features becomes very difficult, and it becomes very easy to forget to test the feature with a specific invalid input parameter.
        - Code works now and in the future : imagine that we've written a very complex and useful method and other developers also using it in their classes as well. How do we make
          sure that other developers who want to make changes to our code do not break anything? Unit tests that we've written for this method will give you confidence that future
          changes to this method do not break existing functionality and this method still works as expected with all valid and invalid input parameters as it used to work before.
        - other code still works even after we made changes : It is also called regression testing, and unit tests help us feel more confident that the new code changes to existing
          application features do not break other working codes. If we make changes to one of the method, and if it breaks functionality in another existing and well tested feature,
          then it is called regression. So we will want to write unit tests to make sure that there is no regression and all features still work as expected, even after we made
          the small change to existing and well working code.

# F.I.R.S.T Principle:
    * When writing unit tests, developers tried to as much as possible for the first principle. It is a combination of multiple principles.
        - Fast - unit tests run fast : unit tests are small pieces of code that perform one specific task and unlike integration tests, unit tests do not communicate over the
          network and do not perform database operations(do not communicate with remote servers and databases). that's why they run very fast.
        - Independent - unit tests are independent : Ideally, unit tests should be independent, from each other. One unit test should not depend on the result produced by another
          unit test. that's why we don't need to run unit tests in an order. The code we're testing or the system under test should also be isolated from its dependencies, and this
          is to make sure that the back end dependency object does not influence a unit test. So dependencies are usually mocked or stubbed with predefined data. And this way unit
          test can test the system under test in isolation from its dependencies and produce a very accurate result.
        - Repeatable - unit tests are repeatable : if we run the same unit test multiple times, it should produce the same result. if unit test is run on a different computer, It
          should also produce the same result. This is why unit tests are made independent, from environment and from other unit tests. The input parameters that the method under
          test requires are usually pretty find at hard coded, and the method that you are testing needs to be tested with valid and invalid input parameters, Then we'll create
          two or three different unit tests. Each unit test will test the method under test with its own set of predefined parameters. And this way unit becomes repeatable.
          And if we run it multiple times in different environments on a different computer or different operating system, it will still produce the same result each time we run it.
        - Self-validating - unit tests validates itself : that means that to learn if unit has passed or not developer should not be doing any additional manual checks after the
          test completes. units will validate the result that method under test has produced itself, and it will itself make a decision whether this unit test is passing or is
          failing. So after unit test completes, the result will be clear.
        - Thorough & Timely(developers who practice TDD will also spell it as timely) - cover edge cases : Talking about Thorough, this means that when testing
          the method, we should consider happy path as well as negative scenario. And this means that most of the time we create multiple unit tests to test a method that accepts
          input parameters. One unit test will test our method with valid input parameters, and another unit test will test our method with invalid input parameters. And if there
          is a range like minimum and maximum value, then we should create additional unit tests to test for minimum and or maximum values as well.
          Talking about Timely, it means that it is better to create unit test at the time you're working on application feature. This way you will have more confidence that the
          feature does work as expected, and there are fewer chances that you will introduce a bug and that bug will be released to production. So before you promote your code to
          production, it should be covered with unit tests.

# Testing Code in Isolation:
    * One of the main principles to follow when write unit tests for a method is that we need to test that method in isolation from its other dependencies.
      ideal scenario is a method has single responsibility and that method isolated from any other external classes/dependencies. If it is isolated from external dependencies,
      then our unit test can easily create an instance of that class and call its method to verify if it returns the expected result. But very often it's not the case. often a method
      can be depended on a one or two methods from another one or more classes. and to access those dependencies, method has to create instances of those other classes inside the
      method or class. this complicates our testing very much, because if a failure takes place in the method that is in a different object, the unit test will also fail. as a result
      we might mistakenly conclude our method under test is misbehaving while actual error is taken place in different method in a different object. So to avoid situations like this,
      we need to isolate the code we're testing. We create unit test to test business logic inside of method under test only not the dependencies as well. and since we're only interested
      in testing selected method, we need to find a way to isolate that method from its dependencies and test it in isolation from the code in other objects.

      solution :
        dependency injection: To make our code better testable, we need to use dependency injection. Let's say we have an object A that depends on code in other objects B and C.
        currently object A creates a new instance of class B and C in one of its methods. So instead of Object A creating a new instance of Class B and C, we will need to lead
        this relationship around and inject a ready to use object B and C into object A. that is called dependency injection.(refer img_6)
        So to isolate code in object A from its dependencies(from code in object B and C), we can write unit test that injects a fake or mock implementation(or simple stubs that do
        not really do any business logic) of dependencies(code in object B and C) into A. This way we can test code in object A in isolation, and this way we prevent situations when
        a backend code in object B fails, so code in object A as well. injecting mock implementation instead of real dependency, helps us isolate code that we need to test with
        different kinds of input values. when our code under test has dependencies on other objects, we need to use dependency injection and inject mock implementations of those
        objects, so that we can test our code in isolation from those dependencies.

        img_7 : instead of this method creating a new instance of signUpWebService, we will make this method, accept this object as a method argument, or we can use other dependency
        injection strategies and inject this dependency using constructor based or field dependency injection.
        img_8 : when we need to write a unit test that test the behavior of a method, we need to isolate that method from other dependencies by injecting these dependencies into our
        method as stubs or mock objects with predefined behavior. We are not interested testing these dependencies. We are only interested in testing code in the method under test
        isolated from all other dependencies. This way our unit test that's testing a particular method is more reliable.

# Testing Pyramid :
    To test our application, you might want to write three different types of test code(refer img_9):

    * Unit test : Notice that unit tests are at the bottom of the test pyramid, and this is because usually unit tests are written and run before any other test code. and usually you
                  will write a lot more unit tests for application than any other tests. When running test code, unit tests are also the fastest among these three. This is because
                  units tests do not need to connect to a database, and they do not need to send that HTTP request over the network and wait for a response.
    * Integration Test : these test methods are very similar to unit tests, except that in this case we will not use mock or fake objects instead of real objects.
                 if method we are testing needs to load data from a database or if it needs to send HTTP request to fetch data from any remote server, we will not use fake objects
                 anymore, we let our function use a real database connection or send a real http request and work with a real http response that it gets from the server.
                 in this case, if we need as deep pass, then we are confident that the method we were testing will work well when it is integrated with other external systems.
                 Integration tests runs slower than unit tests because they need to communicate with other internal or external systems.
    * Automated End-to-End Test : When doing end-to-end testing, we will test software functionality from beginning to end. for example, if we're a building mobile application, or
                 a web page, then we might want to write automated UI tests. These tests will test functionality of our application by actually invoking button clicks and evaluating
                 if that button click did produce an expected result. So automated end-to-end tests are even slower, and the number of end-to-end tests that we create will be a lot
                 less than the number of unit tests.

*/
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}