package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/* How to use @SpringBootTest annotation, to specify if we want Spring framework to run 'DEFINED_PORT web environment'.
    - To make spring framework run embedded server on the port number that we define, we'll need to use a different options for web environment that is called 'DEFINED_PORT'.
    - And there are a couple of different ways to define a port.
        1. define port number in application properties file. (look at src->main->resources->application.properties file)
        2. using 'properties' attribute of @SpringBootTest annotation : if we do not want to use port number that we've defined in 'application.properties' file and want to use a
           different port number for our tests, then there is another way to make a tomcat start on a different port number other than the one we've defined in application.properties.
           the port number that we defined there will override the value that is loaded from 'application.properties' file, and this has a higher priority.
           note: if there are multiple properties that we want to override, then we'll need to surround them with Curly brackets, include property in double quotes, and then
           separate them with a comma.  ex: {properties = "server.port=8081", "hostName=192.168.0.2"}
        3. Loading alternative configurations using @TestPropertySource : when doing integration testing of our application, sometimes we need to make use of alternative configuration.
           for ex: we might need to run our application on a different port number or connect to a database as a different user, or even connect to a different database.
           and one of the ways to make our test methods to use alternative configuration is to use @TestPropertySource annotation.
           @TestPropertySource : this annotation allows us to manually override certain configuration properties just the same way we used @SpringBootTest annotation with
           'properties' attribute. @TestPropertySource annotation also allows us to load configuration properties from another configuration file.
           so if we want to test our application with alternative configuration, we can use @TestPropertySource annotation and configure it to load configuration properties from
           either a different file, or we can override certain properties manually by defining them right inside the @TestPropertySource annotation.                              */


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
/* To load configuration properties from another file, we will need to create another application properties file(right mouse click 'resources' file -> new Resource Bundle without
   '.properties') and specify location of that file in 'locations' attribute of @TestPropertySource.
   so this will make spring framework load application properties from both files, from 'application-test.properties' that we're using here and from default 'application.properties'
   file. but properties that are loaded from this file that is specified here, have a higher precedence than the default 'application.properties' file.
 * To manually override certain configuration properties we can use 'properties' attribute, and in double quotes we can provide the list of properties that we want to override.
   and those have the highest precedence higher than the values specified in 'application-test.properties' file and also default 'application.properties' file.                     */
@TestPropertySource(locations = "/application-test.properties", properties = "server.port=8083")
public class UsersControllerIntegrationTest {

    /* demonstration purpose only: if we need to have access to the value that we load from configuration properties, we can inject it here using @Value. provide the name of the
       property that we want to load from configuration file and assign to this member variable, to see which value from which resource is going to be assigned here.
       - 8080 : default port number that embedded server start on default if we are not specified any port.
       - 8081 : from default 'application.properties' file. (3rd highest precedence)
       - 8082 : from 'application-test.properties' another configuration file. (2nd highest precedence)
       - 8083 : from manually overridden properties using 'properties' attribute of @TestPropertySource. (highest precedence) */
    @Value("${server.port}") private int serverPort;

    @Test void contextLoads() {
        System.out.println("server port:" + serverPort);
    }

}

