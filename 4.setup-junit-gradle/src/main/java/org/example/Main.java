package org.example;

/* 1. how to set up JUnit in Gradle based java project and how to use gradle to execute unit tests.
        - add aggregate junit dependency into build.gradle file
        - change dir to gradlew file and execute test task : ./gradlew clean test
                clean: clean the project/will remove any leftovers from previous builds and will execute the test.
                test: automatically detect and automatically execute all unit tests.                                                    */

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}