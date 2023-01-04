package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.ui.request.UserDetailsRequestModel;
import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/* start working on the test method, that will test if the 'createUser' Api endpoint works and if we can create a new user. (can't run still, move on to the next topic)
    - MockMvcRequestBuilders : when testing controller methods with 'mockMvc', we are not going to send an actual real HTTP requests over the network. Spring Framework provides us
      with a class, which we can use to configure a 'mock HTTP requests'. and it is called 'MockMvcRequestBuilders'. and this will build as a 'RequestBuilder' object.
    - MockMvc : help us to perform(execute) mock http requests('RequestBuilder' objects) created by using 'MockMvcRequestBuilders' class.
    - MvcResult : after executing mock http request, in response we'll get result. and the result is returned in a form of 'mock 'MvcResult' object'. which we can use to read
      response object and from that response object we can read content of response(http status, headers, response body)                                                          */
@WebMvcTest(controllers = UsersController.class, excludeAutoConfiguration = { SecurityAutoConfiguration.class })
public class UsersControllerWebLayerTest {

    //inject MockMvc: to perform mock http request(RequestBuilder object)
    @Autowired private MockMvc mockMvc;

    @Test @DisplayName("User can be created")
    void testCreateUser_whenValidUserDetailsProvided_returnsCreatedUserDetails() throws Exception {

        /* Arrange :::::: So to invoke createUser() method, we will need to send HTTP post request, because it is annotated with @PostMapping to '/users' Api endpoint. and this
        request will need to contain HTTP body, because this method accepts request body. and the content of HTTP request body will be converted into a Java object of
        'UserDetailsRequestModel' data type. and the user details that we will need to send in the body of this HTTP post request are first name, last name, email password and
        repeat password according to the 'UserDetailsRequestModel' class. in this arrange section we'll prepare this HTTP request.                                                */
        UserDetailsRequestModel userDetailsRequestModel = new UserDetailsRequestModel();
        userDetailsRequestModel.setFirstName("kalana");
        userDetailsRequestModel.setLastName("sandakelum");
        userDetailsRequestModel.setEmail("kalana@gmail.com");
        userDetailsRequestModel.setPassword("12345678");
        userDetailsRequestModel.setRepeatPassword("12345678");

        /* when testing controller methods with 'mockMvc', we are not going to send an actual real HTTP requests over the network. Spring Framework provides us with a class, which
        we can use to configure a 'mock HTTP requests'. and it is called 'MockMvcRequestBuilders'. and this will build as a 'RequestBuilder' object.                              */
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(userDetailsRequestModel)); /* configure the content of HTTP request body, which is going to be the 'JSON string' with the
             user details. we can type JSON string manually, or use 'UserDetailsRequestModel' class to create a Java object and then convert that into a string using objectMapper.*/

        /* Act :::::: perform mock http request object(RequestBuilder object) we've prepared in arrange section. And to do that, we'll need to use another object that is called
        'MockMvc'. after executing mock http request, in response we'll get result. and the result is returned in a form of 'mock 'MvcResult' object'. which we can use to read
        response object and from that response object we can read content of response(http status, headers, response body).                                                       */
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        String responseBodyAsString = mvcResult.getResponse().getContentAsString(); //will contain newly created user details as a string
        UserRest createdUser = new ObjectMapper().readValue(responseBodyAsString, UserRest.class); /* convert back into a data type that controller method returns(UserRest):  when
        our createUser() method which is method under test return an object of 'UserRest' data type, spring framework will convert that object back to 'json string' and then return
        it back in HTTP response body. in here we do have response body string, so we can convert it back to an object of 'UserRest' data type using 'objectMapper'.                                   */

        //Assert :::::: work with above 'createdUser' object and validate if it contains correct user details.
        Assertions.assertEquals(userDetailsRequestModel.getFirstName(), createdUser.getFirstName(), "returned user firstName is most likely incorrect");
        Assertions.assertEquals(userDetailsRequestModel.getLastName(), createdUser.getLastName(), "returned user lastName is most likely incorrect");
        Assertions.assertEquals(userDetailsRequestModel.getEmail(), createdUser.getEmail(), "returned user email is most likely incorrect");
        Assertions.assertFalse(createdUser.getUserId().isEmpty(), "userId should not be empty");
    }

}
