package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.security.SecurityConstants;
import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/* How to write a test method, that includes authorization access token in HTTP request to test protected api endpoints : integration test methods which are test all layers together,
will execute together to perform another integration. :) */

/* 3. By default, junit will create a new instance of a test class per each test method that junit executes. so once test method complete executes, for the following test methods,
   the value assigned to a member variable will be null again. that means once test method that test 'user login' complete executes, for the following test methods like 'get users',
   the value assigned to 'authorizationToken' member variable will be null again.
 * And for this not to happen, we need to change test instance lifecycle from default behavior PER_METHOD to be PER_CLASS. this will make all of our test methods in this class share the
   same instance of a class. and that means the value that we assign to 'authorizationToken' member variable by 'user login' test method will be available to other test methods as well.*/
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersControllerIntegrationTest {

    @Autowired TestRestTemplate testRestTemplate;
    private String authorizationToken;

    /* Test method that test 'getUsers()' method in rest controller which has protected Api endpoint, that will test if we can fetch a list of users :
    because our springboot application uses spring security, by default GET '/users' Api endpoint that we test in this method is protected and this means that for us to be able
    to invoke this method and to get list of users, our HTTP GET request will need to contain a valid JWT access token in authorization header. Otherwise, instead of receiving a
    list of users, we will get back as HTTP status code 403 which stands for Forbidden. */
    @Order(4) //2. because this test method will need to include authorization access token in http request, we'll need to run it after we perform user login(after @Order(3)). so we need to order test methods.
    @Test
    @DisplayName("GET /users works")
    void testGetUsers_whenValidJwtProvided_returnsUsers() {
        //arrange : prepare http request that includes authorization token in authorization http header.
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authorizationToken); /* 4. HTTP request that this test method will perform will need to include JWT access token in 'Authorization' http header. so we
        can set authorization header with the token value using 'setBearerAuth()' method that accepts token as a parameter. this method will add http header with a 'Bearer' as a
        prefix to jwt token.
        to provide the value of authorization token to the authorization http header in here we can use 'authorizationToken' member variable that initialize in the method that
        performs 'user login'. test method that performs 'user login' receive jwt token in http response and assign it to that member variable there. so we can use it here. that's
        why test methods that perform/test protected api endpoints(use authorization http header to authorize) like this, needs to run after 'login' test method performed. we can
        do that be ordering test methods. otherwise these test methods will be failed. */

        HttpEntity requestEntity = new HttpEntity(headers); //put these headers into HTTP entity and use it in the request.

        //act : perform http get request that has protected.
        ResponseEntity<List<UserRest>> responseEntity = testRestTemplate.exchange("/users", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<UserRest>>() {
        });

        /* assert : assert that the HTTP response status code is '200' and there is only one user returned to the response body(because there is only one user in the database that
        we've created in this test class, by executing 'user creation' test method) */
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Http status code should be 200"); /* if the authorization access token that we're including in this
        method missing or if it is invalid, in http response we will get 403. but if everything is okay, our response status code will be 200. */
        assertTrue(responseEntity.getBody().size() == 1, "there should be exactly in the list");
    }

    @Order(3)
    @Test
    @DisplayName("/login works")
    void testUserLogin_whenValidCredentialsProvided_returnsJwtInAuthorizationHeader() throws JSONException {
        //Arrange :
        JSONObject loginCredentials = new JSONObject();
        loginCredentials.put("email", "test@test.com");
        loginCredentials.put("password", "12345678");

        HttpEntity<String> request = new HttpEntity(loginCredentials.toString());

        //Act
        ResponseEntity response = testRestTemplate.postForEntity("/users/login", request, null);
        authorizationToken = response.getHeaders().getValuesAsList(SecurityConstants.HEADER_STRING).get(0); /* 1. In this test method that tests 'user login', we receive http
        response that contains http header with a name 'Authorization' and its value. for us to be able to use this 'Authorization' header in all other test methods that needs
        authorization access token test their protected api endpoints, we'll need  to assign it to a class member variable and also make all of our test methods in this test class
        share the same instance of the test class by changing test instance lifecycle to PER_CLASS. then initialization of that class member will happen in here (user login) when
        response received. otherwise token value assign to the class member variable by this 'login' test method will be null again when junit start to execute another test method.*/

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "http status code should be 200");
        assertNotNull(authorizationToken, "response should contain Authorization header with JWT");
        assertNotNull(response.getHeaders().getValuesAsList("UserID").get(0), "response should contain UserID in a response header");
    }

    @Order(2)
    @Test
    @DisplayName("GET /users require JWT")
    void testGetUsers_whenMissingJWT_returns403() {
        //arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON)); /* this header is actually optional, because by default spring framework supports JSON as a media type.
        so if our application is not configured to use XML as default media type for example, then we can actually skip this header.    */

        HttpEntity requestEntity = new HttpEntity(headers);

        //act
        ResponseEntity<List<UserRest>> responseEntity = testRestTemplate.exchange("/users", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<UserRest>>() {
        });

        //assert
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode(), "HTTP status 403 Forbidden should have been returned");
    }

    @Order(1) //this should execute before 'login' and 'get users' test methods executes.
    @Test
    @DisplayName("User can be created")
    void testCreateUser_whenValidUserDetailsProvided_returnsUserDetails() throws JSONException {
        // arrange
        JSONObject userDetailsRequestJson = new JSONObject();
        userDetailsRequestJson.put("firstName", "kalana");
        userDetailsRequestJson.put("lastName", "sandakelum");
        userDetailsRequestJson.put("email", "test@test.com");
        userDetailsRequestJson.put("password", "12345678");
        userDetailsRequestJson.put("repeatPassword", "12345678");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML));

        HttpEntity<String> request = new HttpEntity<>(userDetailsRequestJson.toString(), headers);

        //Act
        ResponseEntity<UserRest> createdUserDetailsResponseEntity = testRestTemplate.postForEntity("/users", request, UserRest.class);
        UserRest createUserDetails = createdUserDetailsResponseEntity.getBody();

        //Assert
        assertEquals(HttpStatus.OK, createdUserDetailsResponseEntity.getStatusCode());
        assertEquals(userDetailsRequestJson.getString("firstName"), createUserDetails.getFirstName(), "returned user's first name seems to be incorrect");
        assertEquals(userDetailsRequestJson.getString("lastName"), createUserDetails.getLastName(), "returned user's last name seems to be incorrect");
        assertEquals(userDetailsRequestJson.getString("email"), createUserDetails.getEmail(), "returned user's email seems to be incorrect");
    }

}