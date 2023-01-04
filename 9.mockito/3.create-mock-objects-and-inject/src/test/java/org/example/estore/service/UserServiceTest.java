package org.example.estore.service;

import org.example.estore.data.UsersRepository;
import org.example.estore.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/* How to initialize mock objects with Mockito and how to inject those mock objects as dependencies to our class under test:
 1. @ExtendWith(MockitoExtension.class) : before we can use Mockito in our test class, we will need to annotate our class with a special annotation called '@ExtendWith()'. This
    will enable us to use Mockito annotations in this class.
 2. @Mock: create mock objects of dependencies of class under test using @Mock annotation, and use them instead of real ones.
 3. @InjectMocks: create a new instance of class under test & then inject mock objects into our class under test using @InjectMocks.
 4. mock the behavior of dependencies of class under test : mock the behavior of the methods that are provided by the class under test's dependencies(mock objects).              */

@ExtendWith(MockitoExtension.class) public class UserServiceTest {

    /* 2. create 'UsersRepository' mock object : because we want mockito to create a mock object that implements this interface, we'll need to annotate this instance variable with
    @Mock annotation. then mockito will automatically create a mock object that implement this 'UsersRepository' interface.                                                       */
    @Mock UsersRepository usersRepository;

    /* 3.1. configured Mockito to inject mock Object(UsersRepository) into our 'UserService' object: now we can inject this 'UsersRepository' object into our 'UserService'
    implementation object. And to do that, we will need to annotate userService property with another annotation that is called @InjectMocks. We will also need to change data
    type of our userService from interface to a class that implements this interface. So instead of 'UserService', we will need to change it to use the 'UserServiceImpl'.
    And this will make mockito create a new instance of 'UserServiceImpl' implementation class, and inject an object of 'UsersRepository' into service class for us.
    UserService userService; -> @InjectMocks UserServiceImpl userService;    (refer img_1,img_2)                                                                                  */
    @InjectMocks UserServiceImpl userService;

    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;

    @BeforeEach void init() {
        /* 3.0. our 'UserServiceImpl' Class expects a dependency which is 'UsersRepository' object. in this code example, we create an instance of our 'UserService' class manually,
        but we can also let Mockito to create a new instance of 'UserService' class for us and inject the 'UsersRepository' object into that object(UserService) automatically for us.
        note: to add dependency(mock object) into class under test this way, we need to inject that dependency as a constructor base dependency in main source class. only then we
        can add that dependency into class under test as a constructor argument.
        userService = new UserServiceImpl(usersRepository);                                                                                                                       */
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
