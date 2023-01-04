package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/* How to use @SpringBootTest annotation, to specify if we want Spring framework to run 'DEFINED_PORT web environment'.
    - To make spring framework run embedded server on the port number that we define, we'll need to use a different options for web environment that is called 'DEFINED_PORT'.
    - And there are a couple of different ways to define a port.
        1. define port number in application properties file. (look at src->main->resources->application.properties file)
        2. using 'properties' attribute of @SpringBootTest annotation : if we do not want to use port number that we've defined in 'application.properties' file and want to use a
           different port number for our tests, then there is another way to make a tomcat start on a different port number other than the one we've defined in application.properties.
           the port number that we defined there will override the value that is loaded from 'application.properties' file, and this has a higher priority.
           note: if there are multiple properties that we want to override, then we'll need to surround them with Curly brackets, include property in double quotes, and then
           separate them with a comma.  ex: {properties = "server.port=8081", "hostName=192.168.0.2"}
        3. Loading alternative configurations using @TestPropertySource.                                                                                                 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = "server.port=8082")
public class UsersControllerIntegrationTest {

    /* demonstration purpose only: if we need to have access to the value that we load from configuration properties, we can inject it here using @Value. provide the name of the
       property that we want to load from configuration file and assign to this member variable, to see which value from which resource is going to be assigned here.
       - 8080 : default port number that embedded server start on default if we are not specified any port.
       - 8081 : from default 'application.properties' file. (2nd highest precedence)
       - 8082 : from manually overridden properties using 'properties' attribute of @SpringBootTest. (highest precedence) */
    @Value("${server.port}") private int serverPort;

    @Test void contextLoads() {
        System.out.println("server port:" + serverPort);
    }

}
