package com.appsdeveloperblog.tutorials.junit.ui.controllers;

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

/* How to make sure the protected API endpoints does indeed return 403 status code which stands for Forbidden, if we do not include a valid access token.
    - To communicate with protected API endpoints, http requests need to include a valid authorization access token. and if we do not provide valid access token, then in response
      we will get back HTTP status code 403 which stands for Forbidden. so we should validate that if we were not provided any authorization valid access token with http request,
      that's going to be sent to a protected api endpoint, will return 403 forbidden or not. in order to test api endpoints that needs to be protected are actually protected or not.
      (to validate api endpoint protections works exactly the way they suppose to)
    - HTTP status code 403 means that server understands the request, but it refuses to authorize it. server to authorize request we will need to provide a valid JWT access token.
    - there is a way to acquire a valid access token to do integration testing for protected api endpoints. we can write a test method to perform user login and once the user
      successfully logs, in HTTP response will contain a valid JWT access token. we can then write another test method to validate that we can use that token to communicate with
      other protected API endpoints to test them.                                                                                                                                 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersControllerIntegrationTest {

    @Autowired TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("GET /users require JWT")
    void testGetUsers_whenMissingJWT_returns403() {

        //arrange
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        //or headers.set("Accept","application/json");

        HttpEntity requestEntity = new HttpEntity(headers);
        /* HttpEntity requestEntity = new HttpEntity(null, headers);  constructor of HttpEntity accepts request body and headers. but because we're going to send the http GET
        request, the body of request will be empty. so when we create HttpEntity, we can either provide null as a body or do not include it at all.   */

        //act
        ResponseEntity<List<UserRest>> responseEntity = testRestTemplate.exchange("/users", HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<UserRest>>() {
        }); /* testRestTemplate.exchange() : we can use 'exchange()' method to send the HTTP GET request and then get back a list of objects.
        testRestTemplate.exchange(<urlPath>, <http method>, <HttPEntity(requestEntity)>, <dataTypeListOfObjectsThatWeWantToGetBack>.
        so this will return us a ResponseEntity that will contain a List of UserRest objects.*/

        //assert : in this case we're only interested in HTTP status code.
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode(), "HTTP status 403 Forbidden should have been returned");
    }

    @Test
    @DisplayName("User can be created")
    void testCreateUser_whenValidUserDetailsProvided_returnsUserDetails() throws JSONException {

        // arrange : prepare http (post) request.
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

        //Act : perform/send http (post) request, using HTTP client 'TestRestTemplate'.
        ResponseEntity<UserRest> createdUserDetailsResponseEntity = testRestTemplate.postForEntity("/users", request, UserRest.class);
        UserRest createUserDetails = createdUserDetailsResponseEntity.getBody();

        //Assert : validate this 'ResponseEntity' object contains correct values. (http response headers, status and body values)
        assertEquals(HttpStatus.OK, createdUserDetailsResponseEntity.getStatusCode());
        assertEquals(userDetailsRequestJson.getString("firstName"), createUserDetails.getFirstName(), "returned user's first name seems to be incorrect");
        assertEquals(userDetailsRequestJson.getString("lastName"), createUserDetails.getLastName(), "returned user's last name seems to be incorrect");
        assertEquals(userDetailsRequestJson.getString("email"), createUserDetails.getEmail(), "returned user's email seems to be incorrect");
    }

}