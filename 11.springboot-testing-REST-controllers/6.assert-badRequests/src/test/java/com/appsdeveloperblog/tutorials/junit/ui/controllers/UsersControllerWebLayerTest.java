package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.service.UsersService;
import com.appsdeveloperblog.tutorials.junit.shared.UserDto;
import com.appsdeveloperblog.tutorials.junit.ui.request.UserDetailsRequestModel;
import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

/* Test that bean validations for our http request body properly set and does work as expected or not:
 * Test if the '/users' Api endpoint responds back with an HTTP status code 400, if we do not satisfy the validations of request body fields by sending invalid values.
   so this will confirm bean validations working properly for our '/users' api endpoint. we need to create a separate test methods for to test bean validations working properly or not.
   (we can configure beans that we wanted to be validated by annotating theirs each field with bean validation annotations(@Email, @Size etc) those are coming from
   'spring-boot-starter-validation' dependency (ex: 'UserDetailsRequestModel' class). and enable those validations by adding @Valid annotation together with @RequestBody to
   validate of request body fields. spring framework will do it automatically for us when we've enabled validations and provide validation annotations for the beans.
 * we do this because there is a chance that while editing these classes, you or someone else on our team might accidentally remove or change one of these validation annotations.
   And if we do not have a test method that validates these requirements, then we will introduce a bug in our code that will remain unnoticed.
 * if we were making changes to our methods in the controller classes(method under tests) or http request body validation classes(in here UserDetailsRequestModel.class), and
   accidentally removed or changed a validation annotation, then validation of an HTTP request body will not work and hopefully our test method will be able to catch it by failing
   the test.
 * to make sure that our application works as expected, we might want to write separate test methods to validate each of these fields.                                            */
@WebMvcTest(controllers = UsersController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class UsersControllerWebLayerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean UsersService usersService;

    @Test @DisplayName("User can be created")
    void testCreateUser_whenValidUserDetailsProvided_returnsCreatedUserDetails() throws Exception {

        //Arrange
        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setFirstName("kalana");
        userDetailsRequestModel.setLastName("sandakelum");
        userDetailsRequestModel.setEmail("kalana@gmail.com");
        userDetailsRequestModel.setPassword("12345678");
        userDetailsRequestModel.setRepeatPassword("12345678");

        UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());
        when(usersService.createUser(any(UserDto.class))).thenReturn(userDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));  //prepare mock http request

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString();
        UserRest createdUser = new ObjectMapper().readValue(responseBodyAsString, UserRest.class);

        //Assert
        Assertions.assertEquals(userDetailsRequestModel.getFirstName(), createdUser.getFirstName(), "returned user firstName is most likely incorrect");
        Assertions.assertEquals(userDetailsRequestModel.getLastName(), createdUser.getLastName(), "returned user lastName is most likely incorrect");
        Assertions.assertEquals(userDetailsRequestModel.getEmail(), createdUser.getEmail(), "returned user email is most likely incorrect");
        Assertions.assertFalse(createdUser.getUserId().isEmpty(), "userId should not be empty");
    }

    /* Test method to assert that if one of the required fields is missing, then our Api endpoint will respond back with a bad request or HTTP status code 400.
     * Testing 'createUser' method & 'UserDetailsRequestModel' class to make sure that requirements for fields are also satisfied:
        - This test method is to test that if user's 'firstName' field in the request is less than 2 characters, then our api endpoint returns http bad request with HTTP status
          code 400. we might need to create a separate test method for that.
        - assume that we were making changes to our methods in the controller class(like createUser()) and request body classes(like UserDetailsRequestModel) that we've set
          validations for request body fields. and if accidentally removed or changed validations(validation annotations) from those, then this test method will fail.
          because validation of an HTTP request body will not work properly, so invalid inputs may not identify and then method under test will proceed request with invalid inputs
          and then return' http 200 OK response' instead 400 bad. so then we know that there is an issue in our code(validations are not properly set), and should fix that until
          this test pass.                                                                                                                                                         */
    @Test @DisplayName("First name is not empty") void testCreateUser_whenFirstNameIsNotProvided_returns400StatusCode() throws Exception {

        //Arrange
        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setFirstName("");  // intentionally set user's firstName to an empty string.
        userDetailsRequestModel.setLastName("sandakelum");
        userDetailsRequestModel.setEmail("kalana@gmail.com");
        userDetailsRequestModel.setPassword("12345678");
        userDetailsRequestModel.setRepeatPassword("12345678");

        UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());
        when(usersService.createUser(any(UserDto.class))).thenReturn(userDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn(); //we can work with 'MvcResult' to validate if it contains correct HTTP status code.

        //Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(), "incorrect HTTP status code returned");
    }

}
