package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/* How to write test methods that will engage all 3 layers(web,service,data) of our application.
    - these test methods are going to be integration tests because we will not mock, and we'll not fake any object behavior.
    - these integration test methods will perform an HTTP request to trigger code in the controller class and a method in the controller class will invoke code in the service class,
      and our code in the service class will actually invoke code in the data layer. and our code in the data layer will actually write and read information from a database table.
    - also we'll not disable any spring security configurations when we do integration tests. that means to communicate with protected Api endpoints, our HTTP request will need to
      include a valid JWT token. (refer img_01)
* @SpringBootTest :
    - to make spring framework create and run application context that involves all 3 layers of our application, we need to annotate test class with annotation that is called
      '@SpringBootTest'.
    - @SpringBootTest test annotation will tell Springboot to look for main application class(one that is annotated with '@SpringBootApplication' annotation and the one that has
      public static void main method), and it will use it to start Spring application context. so Spring Framework will then scan the root package of application and all sub
      packages looking for classes with different annotations. then will create beans for those and all those beans will be added to Sprint application context.
    - because we use @SpringBootTest annotation, spring framework will not stop at the web layer. it will create beans that are related to all 3 layers of our application. beans
      that are related to web layer, service layer and data layer, all beans will be created and all beans will be added to Sprint application context.
    - we can also use this @SpringBootTest annotation to specify, if we want Spring framework to run 'web environment' on a specific port number or on a random port number.        */
@SpringBootTest
public class UsersControllerIntegrationTest {

    @Test void context() {

    }

}
