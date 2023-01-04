package com.company;

/* what is JUnit 5 : it is a testing framework that allows us to write unit tests in Java,
 * Let's have a look at its architecture(refer img). JUnit 5 is a combination of three things.

        - JUnit platform : this is a core platform that serves as a foundation for launching testing frameworks on JVM, and it provides a testing
          engine for developing and running testing framework on the platform. Most popular Java development environments like NetBeans, Intellij, Eclipse and built tools like
          Gradle, Maven and Ant, they all provide first class support for a JUnit platform. And we can use this tools today to run unit tests. So think of it as a platform that
          runs JUnit tests that you create.
        - JUnit Jupiter :  this is a combination of a new programming model and extension model for writing tests and extensions in JUnit 5. So Jupiter is a name of the API and
          Jupiter API is what we used to write Unit tests in Java. These are different test annotations and assertions that we use in our test methods. And then it also has an
          extension API that allows us to create our own version of Test API, that we can use additionally to Jupiter.
        - JUnit Vintage : JUnit vintage is a test engine for running older unit tests. before JUnit 5 there were Junit 3 and 4. So JUnit Vintage provides us with a test engine to
          run Junit test methods written with older version of Junit API. Because of Junit vintage, we can have a project that contains unit tests written with older JUnit API and
          also newer API version called Jupiter.

 * So together these three components make up JUnit 5.                                                                                                                       */

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}