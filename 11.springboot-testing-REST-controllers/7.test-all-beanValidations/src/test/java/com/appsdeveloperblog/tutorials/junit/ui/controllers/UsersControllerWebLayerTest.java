package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.service.UsersService;
import com.appsdeveloperblog.tutorials.junit.shared.UserDto;
import com.appsdeveloperblog.tutorials.junit.ui.request.UserDetailsRequestModel;
import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

/* Testing all the bean validations:
   testing further 'createUser' method in the controller with 'UserDetailsRequestModel' class to make sure that all the other requirements for other fields(validations) are also
   satisfied. to make sure that our application works as expected, we might want to write separate test methods to validate each of those fields(firstName, lastName, email etc.).
   And this is because there is a chance that while editing those classes, you or someone else on our team might accidentally remove or change those validation annotations. And if
   we do not have a separate test methods to validate those requirements, then there is a chance that a serious bug can go through.                                                 */
@WebMvcTest(controllers = UsersController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class UsersControllerWebLayerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean UsersService usersService;

    UserDetailsRequestModel userDetailsRequestModel;

    @BeforeEach void setUp() {
        userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setFirstName("kalana");
        userDetailsRequestModel.setLastName("sandakelum");
        userDetailsRequestModel.setEmail("kalana@gmail.com");
        userDetailsRequestModel.setPassword("12345678");
        userDetailsRequestModel.setRepeatPassword("12345678");
    }

    @Test @DisplayName("User can be created")
    void testCreateUser_whenValidUserDetailsProvided_returnsCreatedUserDetails() throws Exception {

        //Arrange
        UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());
        when(usersService.createUser(any(UserDto.class))).thenReturn(userDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));

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

    /* unlike the above 'User can be created' test method, in the below test we've not mocked the behavior of the mock object's(usersService) methods. because using below test
    method we're testing only validations for http request body fields we've set, work as expect by providing invalid values and asserting that 'bad request' response returned or
    not. to ensure validations(in the UserDetailsRequestModel) have been set properly or not.
    * if we've properly set validations, then this test will pass before even go through the method under test's(method in controller class) code. As a first thing spring validates
    (using @Valid) Http request body fields right after the 'createUser()' method which is method under test get called by test. when the invalid inputs identified by the validations,
    it'll return '400 bad request' response and further code of method under test won't be even execute and test method will be passed.
    * this way will work as far as validations properly worked and identified invalid values and test passed. but if we've not properly set validations, invalid inputs won't be
    identified and method under tests code will be continued to execute unlike before and '200 OK' response will be returned instead 'bad request' response. so since we're asserting
    '400 bad request' response return for invalid inputs, our test will be also failed.
    * so since method under test's code executed now, and since we've not mocked the mock object's behavior, mock object's methods will return null and method under test won't work
    properly. and it'll cause error instead of failing test. so we do not see test report logs for validation related. so remember to mock the behaviors of mock object's methods
    even because of if it works well for only passing path without it. there is an also failing path.
    (try it by commenting out @Size annotation from 'firstName' field of 'UserDetailsRequestModel' class and testing test method with & without mocking behavior of mock object's
    method which is 'usersService.createUser()')                                                                                             */
    @Test @DisplayName("First name is not less than 2 characters")
    void testCreateUser_whenFirstNameIsLessThan2Characters_returns400StatusCode() throws Exception {

        //Arrange
        //userDetailsRequestModel.setFirstName("kal");
        userDetailsRequestModel.setFirstName("k");

//        UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
//        userDto.setUserId(UUID.randomUUID().toString());
//        when(usersService.createUser(any(UserDto.class))).thenReturn(userDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(), "incorrect HTTP status code returned");
    }

    @Test @DisplayName("Last name is not less than 2 characters")
    void testCreateUser_whenLastNameIsLessThan2Characters_returns400StatusCode() throws Exception {

        //Arrange
        userDetailsRequestModel.setLastName("s");

        UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());
        when(usersService.createUser(any(UserDto.class))).thenReturn(userDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(), "incorrect HTTP status code returned");
    }

    @Test @DisplayName("Email is a valid email")
    void testCreateUser_whenInvalidEmailProvided_returns400StatusCode() throws Exception {

        //Arrange
        userDetailsRequestModel.setEmail("kalana");

        UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());
        when(usersService.createUser(any(UserDto.class))).thenReturn(userDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(), "incorrect HTTP status code returned");
    }

    @Test @DisplayName("password is greater than 2 characters")
    void testCreateUser_whenPasswordLessThan2Characters_returns400StatusCode() throws Exception {

        //Arrange
        userDetailsRequestModel.setPassword("1");

        UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());
        when(usersService.createUser(any(UserDto.class))).thenReturn(userDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(), "incorrect HTTP status code returned");
    }

    @Test @DisplayName("password is less than 16 characters")
    void testCreateUser_whenPasswordGreaterThan16Characters_returns400StatusCode() throws Exception {

        //Arrange
        userDetailsRequestModel.setPassword("12345678912345678");

        UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());
        when(usersService.createUser(any(UserDto.class))).thenReturn(userDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(), "incorrect HTTP status code returned");
    }

    @Test @DisplayName("repeat password is greater than 2 characters")
    void testCreateUser_whenRepeatPasswordLessThan2Characters_returns400StatusCode() throws Exception {

        //Arrange
        userDetailsRequestModel.setRepeatPassword("1");

        UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());
        when(usersService.createUser(any(UserDto.class))).thenReturn(userDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(), "incorrect HTTP status code returned");
    }

    @Test @DisplayName("repeat password is less than 16 characters")
    void testCreateUser_whenRepeatPasswordGreaterThan16Characters_returns400StatusCode() throws Exception {

        //Arrange
        userDetailsRequestModel.setRepeatPassword("12345678912345678");

        UserDto userDto = new ModelMapper().map(userDetailsRequestModel, UserDto.class);
        userDto.setUserId(UUID.randomUUID().toString());
        when(usersService.createUser(any(UserDto.class))).thenReturn(userDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel));

        //Act
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        //Assert
        Assertions.assertEquals(HttpStatus.BAD_REQUEST.value(), mvcResult.getResponse().getStatus(), "incorrect HTTP status code returned");
    }

}
