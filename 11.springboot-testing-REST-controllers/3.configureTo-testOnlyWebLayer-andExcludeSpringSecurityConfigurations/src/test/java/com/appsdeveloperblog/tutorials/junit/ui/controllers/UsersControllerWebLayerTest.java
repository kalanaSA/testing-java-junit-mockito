package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

/* How to configure to test only Web layer and exclude Spring security Configurations:
    - create test classes in the 'test' directory to be in the same package as the rest controller.
    - we've named the test class as '<class-name>WebLayerTest'. because we're going to test only web layer we've added 'WebLayer' additionally, unlike usual test classes
      naming convention. ('<class-name>Test')

 * @WebMvcTest() :
    - to tell Spring Framework that we want to create application context only for those beans that are related to web layer, we'll annotate this test class with a special
      annotation that is called a @WebMvcTest.
    - what spring framework will do is it will start scanning classes in our application looking for different annotations. And then depending on how we've annotated our application
      classes, spring framework will create beans, and it will put them into application context. but in this case because we've annotated our test class with @WebMvcTest annotation,
      it will tell Spring Framework, that we're interested in testing web layer only. so it will make Spring Framework Scan our springboot application only for a limited number of
      classes, classes that are related to web layer only. For example, it will scan and create controller beans, but it will not create data or service layer beans. and this will
      make our test class run a bit faster than full integration test.
 * @WebMvcTest(controllers = UsersController.class) : to configure @WebMvcTest annotation to work with the only one controller class.
    - since we're interested in testing 'UsersController' only(only one controller class), we can limit this annotation to work with only one controller class that we need, using
      'controllers' property.
    - after our @WebMvcTest annotation is configured to work with the only one controller class, then even if our application has more than one controller class, only this one
      controller will be placed into application context. But if we do not limit @WebMvcTest annotation to only one controller class, then all controllers of our springboot
      application will be included into application context.

 * @AutoConfigureMockMvc(addFilters = false) : to exclude spring security filters. (refer img_01)
    - will disable full autoconfiguration and instead apply only configuration that is relevant to MVC tests. ie: will scan and create beans for @Controller @ControllerAdvice,
      @JsonComponent, converter/GenericConverter, filter,WebMvcConfigure, HandlerMethodArgumentResolver beans but not @Component, @Service, @Repository beans.
    - if our application that we're testing uses Spring security, and if there are security filters enabled, and at this moment if we're testing web layer only, and we're not
      interested in those security filters at this time, so we can exclude them using '@AutoConfigureMockMvc()' annotation with 'addFilters = false' property.
    - this @AutoConfigureMockMvc annotation is actually a part of @WebMvcTest annotation. if we open @WebMvcTest annotation, then above the interface we can see list of annotations
      that are already included and @AutoConfigureMockMvc is one of them. we can add this annotation separately if we want spring security to autoconfigure spring MVC infrastructure
      without including security filters. but normally we do not need to add @AutoConfigureMockMvc annotation separately to exclude spring security filters. we can use
      @WebMvcTest property called is 'excludeAutoConfiguration'.
 * @WebMvcTest(excludeAutoConfiguration = { SecurityAutoConfiguration.class }) : another way to exclude spring security autoconfiguration without adding @AutoConfigureMockMvc
   annotation separately.

 * @WebMvcTest(controllers = UsersController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class }) : configured to test web layer only and excluding Spring security
   configuration.                                                                                                                                                                   */

@WebMvcTest(controllers = UsersController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class UsersControllerWebLayerTest {

}
