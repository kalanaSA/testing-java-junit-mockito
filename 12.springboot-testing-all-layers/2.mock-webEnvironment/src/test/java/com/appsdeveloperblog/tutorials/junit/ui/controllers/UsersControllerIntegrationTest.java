package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/* How to use @SpringBootTest annotation, to specify if we want Spring framework to run 'MOCK web environment'.
   - When we annotate our test class with @SpringBootTest, we can also specify the type of web environment that we want to use for our test.
   - If we explicitly specify a 'MOCK' web environment, or if we do not specify any web environment, then by default a 'MOCK' web environment will be used.
   - When a MOCK web environment is used, then spring framework will create web application context with mocked servlet environment. and this means that we'll not have an entire
     spring application context loaded and only beans that are related to web layer will be created.
     And because this is only a mock servlet environment, spring framework will also not start a real embedded servlet container. and to test our web points we'll need to use mock
     MVC object and mock http requests as we did when testing only REST controllers.
 * But integration tests involves codes in all layers of our application. that means entire spring application context needs to be loaded and beans that are related to all layers
   needs to be created unlike 'mock servlet environment' that is not have an entire spring application context loaded and only have created beans that are related to web layer.
   so if we do not want to use MOCK servlet environment anymore, and we want spring framework to actually start an embedded server on a specific port number or on a random port
   number, we'll need to use a different web environment here.                                                                                                                    */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK) //MOCK web environment
public class UsersControllerIntegrationTest {

    @Test void context() {

    }

}
