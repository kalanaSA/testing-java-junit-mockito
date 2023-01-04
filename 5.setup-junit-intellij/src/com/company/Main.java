package com.company;

/* 2. how to set up Junit using popular development environments like IntelliJ.
        how to create a new unit test using IntelliJ development environment, in a java project that does not use Maven or Gradle.
            - create a directory called 'test' and mark that directory as -> 'test sources root' to let IntelliJ know that we want to use this directory for test classes.
            - right mouse click method under test -> generate -> test.. -> select junit version -> select 'fix JUnit 5' in order to add Junit5 library to the project
              classpath automatically by IntelliJ if it's not in there already(refer img & img_1)
      */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}