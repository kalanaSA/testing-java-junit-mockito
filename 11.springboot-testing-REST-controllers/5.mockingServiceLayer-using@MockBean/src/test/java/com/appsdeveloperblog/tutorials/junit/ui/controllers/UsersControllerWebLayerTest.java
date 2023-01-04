package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.service.UsersService;
import com.appsdeveloperblog.tutorials.junit.shared.UserDto;
import com.appsdeveloperblog.tutorials.junit.ui.request.UserDetailsRequestModel;
import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/* How to mock service layer when integration testing RESt controllers in a springboot application:
 * @MockBean : we'll use this annotation to create mock objects for our method under test's dependencies.
    - even we're working on integration testing, still we're testing integration of our code with Spring framework web layer only(we're testing controller methods are correctly
      integrated with RestController and with spring framework features related to RestController. how it works in integration with spring framework components in the web layer).
      we're not testing and not interested in invoking code in the service layer, or the data layer. so to test a method under test in controller class isolation from its
      dependencies instead of using real implementations of those dependencies(from service layer), we'll mock those dependency objects and use those mock objects instead.
      we mock dependencies usually in unit tests. but in this case we're testing web layer works well integrating with spring framework without testing other layers. so to isolate
      web layer related codes from its dependencies, we have to mock those dependencies even in the integration test.
 * @Qualifier | @MockBean({ <class1>,<class2> }) :
    - when we annotated dependency with @MockBean annotation, it will create a mock object for the class that implements that interface which is dependency for controller.
      but if there are more classes that implements the same interface, they will need to use @Qualifier annotation.
      for example, 'UserService' is an interface which is dependency for the 'UsersController' and the only class that implements this interface is 'UserServiceImpl'. but if there
      are other classes that implements the same interface, they will need to use @Qualifier annotation.
    - Or we can also use @MockBean annotation on the class level, and inside curly brackets we'll provide a list of all classes that it needs to mock. ex: @MockBean({ UserServiceImpl.class })
      For example, in this case, it will be 'UsersServiceImpl' class. if there are more classes, then we can add list of other classes there by separating comma. then on the field
      level, need to autowire the mock objects that we've created on the class level.(refer img_01)
    - So it is either you list @MockBean above the class and then auto wire them using @AutoWired annotation, or you do not use @MockBean at the class level and simply use a single
      @MockBean annotation above the field. so this will create a mock object for 'userService'.
 * difference between @MockBean annotation and a regular mockito's @Mock annotation :
    - @MockBean annotation will create mock object, and it will automatically put this mock object into spring application context. But regular mockito's @Mock annotation will not
      automatically place mock object into spring application context. in here, because we're testing springboot application, and we do need mock object in Spring application
      context, we need to use @MockBean annotation instead of @Mock.                                                                                                              */
@WebMvcTest(controllers = UsersController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class UsersControllerWebLayerTest {

    @Autowired private MockMvc mockMvc;

    /* To isolate web layer related code(the code inside 'createUser()' method that's annotated with @PostMapping) from real implementation of 'userService' dependency which
    is from service layer,  we'll need to mock 'userService' object. and to do that we'll use @MockBean annotation. it will create a mock object for the object that implements
    'UserService' interface. in here for the 'UsersServiceImpl' class.                                                                                                                    */
    @MockBean UsersService usersService;  //we've created mock object for 'usersService', so we can mock its behaviors(methods).

    /* so if this test passed, it proves that our createUser() method in the controller class will trigger when an HTTP post request is sent to a '/users' Api endpoint. and it
    proves this method under test can successfully read user details from http request body and bean validations for those fields in request body also works and, so integration of
    this method with Spring framework does also work. that means REST controller class is well integrated with Sprint Framework.                                                  */
    @Test @DisplayName("User can be created")
    void testCreateUser_whenValidUserDetailsProvided_returnsCreatedUserDetails() throws Exception {

        //Arrange:::::::::
        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setFirstName("kalana");
        userDetailsRequestModel.setLastName("sandakelum");
        userDetailsRequestModel.setEmail("kalana@gmail.com");
        userDetailsRequestModel.setPassword("12345678");
        userDetailsRequestModel.setRepeatPassword("12345678");

        /* because 'usersService' is now a mock, we want to mock the 'createUser()' method, and we want to make it return a predefined 'UserDto' object. so first need to create a
        predefined 'UserDto' object and make our 'usersService' mock object return a predefined object instead.
        UserDto userDto = new UserDto();
        userDto.setFirstName("kalana");
        userDto.setLastName("sandakelum");
        userDto.setEmail("kalana@gmail.com");
        userDto.setPassword("12345678");
        userDto.setUserId(UUID.randomUUID().toString());                                                                                                                            */

        //because we do have a same user details already in above 'userDetailsRequestModel' object, we can map all information from that object into this 'userDto' object using 'ModelMapper'. this way our code will be shorter.
        UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString()); //but it doesn't have userId, so we'll need to set userId manually.
        //when 'usersService' object is used to call 'createUser()' method with any object of 'UserDTO' type as a parameter, then we will return the predefined 'userDto' object.
        when(usersService.createUser(any(UserDto.class))).thenReturn(userDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));

        //Act:::::::::::::
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        UserRest createdUser = new ObjectMapper().readValue(responseBodyAsString, UserRest.class);

        //Assert::::::::::
        Assertions.assertEquals(userDetailsRequestModel.getFirstName(), createdUser.getFirstName(), "returned user firstName is most likely incorrect");
        Assertions.assertEquals(userDetailsRequestModel.getLastName(), createdUser.getLastName(), "returned user lastName is most likely incorrect");
        Assertions.assertEquals(userDetailsRequestModel.getEmail(), createdUser.getEmail(), "returned user email is most likely incorrect");
        Assertions.assertFalse(createdUser.getUserId().isEmpty(), "userId should not be empty");
    }

}
