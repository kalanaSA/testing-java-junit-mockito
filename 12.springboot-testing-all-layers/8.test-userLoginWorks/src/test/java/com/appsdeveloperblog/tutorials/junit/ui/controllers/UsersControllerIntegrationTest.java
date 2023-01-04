package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.security.SecurityConstants;
import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/* Test that we can successfully log in and get back authorization access token. then we will be able to use that authorization access token to communicate with protected API
   endpoints.                                                                                                                                                                       */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersControllerIntegrationTest {

    @Autowired TestRestTemplate testRestTemplate;

    /* To check user authentication is successful work?, we need to check first if http response status code is 200, and if http response contains 'Authorization' header and header
       with a name 'UserID' and if the values are not empty.
     * if we do run this test, then there is no guarantee that this will pass. that's because for this to pass, there must be a user with these credentials in the database.
       if this test method executed without creating and storing the new user in database, then 'login' will fail because there is no such user in the database, and http response
       status code will be '403 FORBIDDEN' instead of '200 OK'. so this test method will be failed due to the assertions' failure. (run test class & see)
       so for this test method to pass, we'll need to first create the new user and then perform a user login. Or we can use the test method that validates 'user creation' that is
       creates a user, cooperatively with 'user login' test method by reordering these test methods. then test method that validates 'user login' is executed after 'user creation'
       and 'user login' test method will be passed. (next lesson)
       (in here this '/login works' test method should run after the 'User can be created' test method executed).                                                                        */
    @Test
    @DisplayName("/login works")
    void testUserLogin_whenValidCredentialsProvided_returnsJwtInAuthorizationHeader() throws JSONException {
        //Arrange : prepare user login credentials
        JSONObject loginCredentials = new JSONObject();
        loginCredentials.put("email", "test@test.com");
        loginCredentials.put("password", "12345678");

        //wrap user details JSONObject into an HttpEntity, so that we can use it in an HTTP request.
        HttpEntity<String> request = new HttpEntity(loginCredentials.toString());

        //Act
        ResponseEntity response = testRestTemplate.postForEntity("/users/login", request, null); /* postForObject : we could also use 'postForObject' method, but
        because in response we want to receive an object, from which we can read the http status code and headers. so we need use 'postForEntity' method which returns
        ResponseEntity object that we can read headers & status code to assert.
        url : will be '/users/login' as we've configured in 'WebSecurity.getAuthenticationFilter' method. (default login url path is just '/login'. we do not need to create a
        separate method in the controller class that handles http post request sent to '/login', this is provided to us by default by spring framework, but if needed we can change
        this default URL path to something different).
        responseType (data type which we want the response body to be converted) : because there will be no JSON payload that will be sent back in the response body, we can
        simply provide null there.*/

        /* if login successful, then response status code will be 200 and our application will add a couple of response headers to the http response. and we've configured it in
        'AuthenticationFilter.successfulAuthentication()' in our application. (have a look) */

        //Assert : validate http response status 200 & http response contains 'Authorization' & 'UserID' headers and their values are not empty.
        assertEquals(HttpStatus.OK, response.getStatusCode(), "http status code should be 200");
        assertNotNull(response.getHeaders().getValuesAsList(SecurityConstants.HEADER_STRING).get(0), "response should contain Authorization header with JWT");
        assertNotNull(response.getHeaders().getValuesAsList("UserID").get(0), "response should contain UserID ina a response header");
    }

    @Test @DisplayName("GET /users require JWT") void testGetUsers_whenMissingJWT_returns403() {
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

    @Test @DisplayName("User can be created") void testCreateUser_whenValidUserDetailsProvided_returnsUserDetails() throws JSONException {
        // arrange
        JSONObject userDetailsRequestJson = new JSONObject();
        userDetailsRequestJson.put("firstName", "kalana");
        userDetailsRequestJson.put("lastName", "sandakelum");
        userDetailsRequestJson.put("email", "kalana@test.com");
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