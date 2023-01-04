package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

/* How to use @SpringBootTest annotation, to specify if we want Spring framework to run 'RANDOM_PORT web environment'.
    - how to configure our @SpringBootTest web environment to run on a random port number. and this will help us avoid potential port number conflicts when we execute multiple
      integration tests in the same test environment.
    - To avoid port number conflicts in test environment, it is better to configure our web environment to use a random port number instead of a defined port.
      This will make the embedded server always start on a random port number, and it will allow different integration tests to run in parallel.
    - Also, when using a random port number, there is no need to define specific port number in default 'application.properties' file or in 'application-test.properties' which is
      alternative configuration file. even though we have a defined port number there, when we run our test methods, our application will still start on a random port number.
      this is because in our test class, in the @SpringBootTest configuration, we've specified that we want web environment to use a random port number and this configuration has a
      higher priority.
      (behind the scenes spring framework will make defined port number in 'application.property' file to be zero because this is how we configure springboot applications to start
      on a random port number. but at the end when our embedded server starts, it will not start on port number zero. it will start on a random port number that will be available
      to use.) */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersControllerIntegrationTest {
    @Value("${server.port}") private int serverPort;

    /* if we need to know on which port number our embedded server is actually running, then we can inject the running port number into test class using @LocalServerPort. it'll
       pick up the actual port number on which our embedded server is running, and it will assign that value to this member variable.
     * @Value annotation will help us read value of server port property that is loaded from the properties file and the @LocalServerPort annotation will allow us to read value
       of actual port number on which our server is running. */
    @LocalServerPort private int localServerPort;

    @Test void contextLoads() {
        System.out.println("server port:" + serverPort); //will be zero.
        System.out.println("local server port:" + localServerPort);
    }

}