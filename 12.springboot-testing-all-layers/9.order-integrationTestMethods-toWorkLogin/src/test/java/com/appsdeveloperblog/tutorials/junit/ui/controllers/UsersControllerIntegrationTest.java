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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/* By default, test methods are executed in deterministic but not obvious way. and because we did not apply any specific order to our test methods, we cannot really guarantee which
   test method will execute first and which test method will execute second. so by default, we cannot guarantee that test method that 'creates a new user' will execute first,
   and the test method that performs 'user login' will execute after the user is created. and in this particular case, we rely on the order in which test methods are executed.
 * in this case to pass test method that validate 'user login', we need to run test method that validate 'user creation' first. then created user while executing 'user creation'
   test method, will be available in in-memory database to perform 'user login' test method.
 * in our case, first we want user to be created, second want to perform user login and get authorization access token, and third want to use that access token to test
   communication with protected API endpoints. and if the order is different, our test methods will start failing.
 * After applied specific order to test methods, we can run all of them and see they being executed in the order that we wanted them to execute.
 * note: because our application uses in-memory database at this moment, after our test methods finish running, all data in in-memory database will be deleted. and this means that
   we should be able to run the same test methods again and again, and they should still pass.      */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //annotate test class with '@TestMethodOrder' and specify 'MethodOrderer' as 'OrderAnnotation'. then we can use @Order annotation to apply order to our test methods.
public class UsersControllerIntegrationTest {

    @Autowired TestRestTemplate testRestTemplate;

    //test method that performs user login will execute third. this should execute only after user creation.
    @Order(3)
    @Test
    @DisplayName("/login works")
    void testUserLogin_whenValidCredentialsProvided_returnsJwtInAuthorizationHeader() throws JSONException {
        //Arrange : make sure to add the user credentials that contain the same user details we've added when user creation.
        JSONObject loginCredentials = new JSONObject();
        loginCredentials.put("email", "test@test.com");
        loginCredentials.put("password", "12345678");

        HttpEntity<String> request = new HttpEntity(loginCredentials.toString());

        //Act
        ResponseEntity response = testRestTemplate.postForEntity("/users/login", request, null);

        //Assert
        assertEquals(HttpStatus.OK, response.getStatusCode(), "http status code should be 200");
        assertNotNull(response.getHeaders().getValuesAsList(SecurityConstants.HEADER_STRING).get(0), "response should contain Authorization header with JWT");
        assertNotNull(response.getHeaders().getValuesAsList("UserID").get(0), "response should contain UserID ina a response header");
    }

    //Once the user is created, will execute this method that validates if JWT is missing, then API endpoint responds back with 403 FORBIDDEN.
    @Order(2)
    @Test
    @DisplayName("GET /users require JWT")
    void testGetUsers_whenMissingJWT_returns403() {
        //arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity requestEntity = new HttpEntity(headers);

        //act
        ResponseEntity<List<UserRest>> responseEntity = testRestTemplate.exchange("/users", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<UserRest>>() {
        });

        //assert
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode(), "HTTP status 403 Forbidden should have been returned");
    }

    //user creation should execute first. this should execute before 'user login' test method executes, otherwise it'll be failed.
    @Order(1)
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