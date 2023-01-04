package com.appsdeveloperblog.tutorials.junit.ui.controllers;

import com.appsdeveloperblog.tutorials.junit.ui.response.UserRest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

//run this test method with debug mode to see if all layers will be executed.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersControllerIntegrationTest {

    /* To send http requests, we can use an HTTP client that is called 'RestTemplate'. it is a http client that allows us to send http requests and receive back http responses.
       so we can use the 'RestTemplate' rest client to test our api as well.
     * The 'TestRestTemplate' object that we've injected here is an alternative to 'RestTemplate'. and although these two objects are very similar, the 'TestRestTemplate' is a
       different object, and it does not extend a regular 'RestTemplate'. so we can use both of these objects to test our application, but for testing it is easier and simpler to
       use 'TestRestTemplate', especially when user authentication is involved, and we need to include username and password. */
    @Autowired TestRestTemplate testRestTemplate; //so to use 'TestRestTemplate' to send http requests, we need to inject it to our test class using @Autowired annotation.

    //If all user details are provided, then a new user should be created. and in this case won't mock any dependency object, and user details will actually be stored in the database.
    @Test @DisplayName("User can be created")
    void testCreateUser_whenValidUserDetailsProvided_returnsUserDetails() throws JSONException {

        // arrange ::::::::::::: prepare http (post) request.
        /* prepare json object (include user details) that we'll use in an HTTP (post) request body to create a new user. to prepare user details we can type JSON manually, or we
        can create 'Json Java Object' and then convert that JSON object into a string. */
        //String createUserRequestJson = "{\" firstName \": \" kalana \",\" lastName \":\" sandakelum \",\" email \":\" kalana @test.com \",\" password \":\" 12345678 \",\" repeatPassword \":\" 12345678 \"}";
        JSONObject userDetailsRequestJson = new JSONObject();
        userDetailsRequestJson.put("firstName", "kalana");
        userDetailsRequestJson.put("lastName", "sandakelum");
        userDetailsRequestJson.put("email", "kalana@test.com");
        userDetailsRequestJson.put("password", "12345678");
        userDetailsRequestJson.put("repeatPassword", "12345678");

        //prepare http headers that our http request might also need. to set http headers we can use 'HttpHeaders' object.
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); //set 'Content-Type' http header with 'APPLICATION_JSON' value.
        headers.setAccept(
            Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)); //set list of 'Accept' http headers with 'APPLICATION_JSON' &  'APPLICATION_XML' values.

        /* so we're ready to use http headers & body in HTTP (post) request. but to send a http request using 'testRestTemplate' object, We need to put our http headers and http
        body(user details Json string) into a single 'HttpEntity' object. constructor of 'HttpEntity' accepts http request body as (json) string and http headers as HttpHeaders
        object. we can use this 'HttpEntity' object with 'testRestTemplate' object to send an HTTP (post) request. */
        HttpEntity<String> request = new HttpEntity<>(userDetailsRequestJson.toString(), headers);

        //Act :::::::::::::: perform/send http (post) request, using HTTP client called 'TestRestTemplate'.
        ResponseEntity<UserRest> createdUserDetailsResponseEntity = testRestTemplate.postForEntity("/users", request, UserRest.class); /* testRestTemplate.postForEntity:  to
        send http post request and get back ResponseEntity. ex: postForEntity(<url>, <HttpEntity-object/ReadyMadeRequest>, <DataTypeOfResponseBodyObject>)
        * (if we have a look at the 'createdUser()' method in rest controller that handles http post request, we can see we are getting back 'UserRest' object as a response when
        we send HTTP post request to '/users' endpoint. this 'UserRest' object will then be converted into a json string and returned to the body of http response.)
        if we not configure http client TestRestTemplate to convert this json string to a needed Java object right away, we'll get json string which we can then convert
        into Java object and work with that Java object.
        so we can configure our http client 'testRestTemplate' to convert this json string right into a needed Java object. (in here an object of 'UserRest' data type, which we
        need to work with). so as a return value we can specify 'ResponseEntity' that will hold 'UserRest' object. ex: ResponseEntity<UserRest>
        then http response body object will be 'UserRest' type.   */
        UserRest createUserDetails = createdUserDetailsResponseEntity.getBody(); /* Now the 'ResponseEntity' object will allow us to get information about HTTP response. we can
        use this object to get an HTTP response status code, headers and response body. from this 'ResponseEntity' object we can get body of HTTP response and  that will contain
        'UserRest' object. */

        //Assert ::::::::::: validate this 'ResponseEntity' object contains correct values. (http response headers, status and body values)
        assertEquals(HttpStatus.OK, createdUserDetailsResponseEntity.getStatusCode());
        //assert user details in the response body do matches the details that were submitted in the original HTTP post request.
        assertEquals(userDetailsRequestJson.getString("firstName"), createUserDetails.getFirstName(), "returned user's first name seems to be incorrect");
        assertEquals(userDetailsRequestJson.getString("lastName"), createUserDetails.getLastName(), "returned user's last name seems to be incorrect");
        assertEquals(userDetailsRequestJson.getString("email"), createUserDetails.getEmail(), "returned user's email seems to be incorrect");
    }

}