package org.example.estore.service;

import org.example.estore.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//classes: ClassNameTest{} | UserServiceTest
//methods: testMethodName(){} | testCreateUser

public class UserServiceTest {

    UserService userService;
    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;

    @BeforeEach void init() {
        userService = new UserServiceImpl();
        firstName = "kalana";
        lastName = "sandakelum";
        email = "kalana@gmail.com";
        password = "kalana123";
        repeatPassword = "kalana123";
    }

    @DisplayName("User object created") @Test void testCreateUser_whenUserDetailsProvided_shouldReturnsUserObject() {
        //Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        //Assert
        assertNotNull(user, "createUser() shouldn't have returned null");
        assertEquals(firstName, user.getFirstName(), "User's first name is incorrect");
        assertEquals(lastName, user.getLastName(), "User's last name is incorrect");
        assertEquals(email, user.getEmail(), "User's email is incorrect");
        assertNotNull(user.getId(), "User id Shouldn't be null");
    }

    //negative scenarios
    @DisplayName("Empty first name causes correct exception") @Test void testCreateUser_whenFirstNameEmpty_shouldThrowIllegalArgumentException() {
        //Arrange
        String firstName = "";
        String expectedExceptionMsg = "User's first name is empty";

        //Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            //Act
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "Empty first name should have caused an Illegal Argument Exception");

        //Assert
        assertEquals(expectedExceptionMsg, thrown.getMessage(), "Exception error msg is not correct");
    }

}

/* remember that in the test driven development approach, we stop working on a test method as soon as it does not compile or fails. */