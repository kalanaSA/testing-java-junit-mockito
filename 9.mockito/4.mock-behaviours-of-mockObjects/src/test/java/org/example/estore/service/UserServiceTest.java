package org.example.estore.service;

import org.example.estore.data.UsersRepository;
import org.example.estore.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/* How to initialize mock objects with Mockito | how to inject those mock objects as dependencies to our class under test |
         mock behaviours of those mock objects ( when().thenReturn() ) :

 1. @ExtendWith(MockitoExtension.class) : before we can use Mockito in our test class, we will need to annotate our class with a special annotation called '@ExtendWith()'. This
    will enable us to use Mockito annotations in this class.
 2. @Mock: create mock objects of dependencies of class under test using @Mock annotation, and use them instead of real ones.
 3. @InjectMocks: create a new instance of class under test & then inject mock objects into our class under test using @InjectMocks.
 4. mock the behavior of class under test's dependencies : mock the behavior of the methods that are provided by the dependencies of class under test which are mocked(mock objects
    now) and injected to class under test.
        - Mockito.when(METHOD(Mockito.any(ARGUMENT), Mockito.any(ARGUMENT))).thenReturn(RETURN_VALUE);
        - Mockito.when(METHOD())
        - Mockito.any(ARGUMENT) : How to stub a method using Mockito's builtin any() arguments matcher: we've used @Mock annotation to tell mockito to create a mock object that
          implements UsersRepository interface. because usersRepository is now a mock object, and it is not a real one, we need to tell mockito what to do when we invoke methods
          that are defined in this interface.
        - .thenReturn(RETURN_VALUE) | .thenAnswer() | .thenThrow() | .thenCallRealMethod()
                                                                                                                                                                                     */

@ExtendWith(MockitoExtension.class) public class UserServiceTest {

    @Mock UsersRepository usersRepository;
    @InjectMocks UserServiceImpl userService;
    String firstName;
    String lastName;
    String email;
    String password;
    String repeatPassword;

    @BeforeEach void init() {
        firstName = "kalana";
        lastName = "sandakelum";
        email = "kalana@gmail.com";
        password = "kalana123";
        repeatPassword = "kalana123";
    }

    @DisplayName("User object created") @Test void testCreateUser_whenUserDetailsProvided_shouldReturnsUserObject() {
        /* 4. Arrange : mock the behavior of 'save' methods that is provided by 'UsersRepository' interface which is dependency for class under test. and we've created mock object
           of it and have injected to class under test.
        * Mockito.when(METHOD(Mockito.any(ARGUMENT))).thenReturn(RETURN_VALUE); - when 'save' method is called on the 'usersRepository' mock object, then we want mockito to return
          true.
        * usersRepository.save(Mockito.any(User.class)) - 'save' method accepts a parameter, and in our case it is a 'User' object. And because we do not invoke real 'save' method,
          it does not really matter what We're going to pass there as 'User' object. so we can create an object of 'User' class and then stub it with any values we want. so to stub
          by a method in mockito, we can use mockito's builtin 'arguments matcher' which is  Mockito.any(dataType_Or_NameOfTheClass). So with this line of code, we tell mockito
          that it can use any object of User class, and we do not really care about values that User object has.
        * note : to see that the real 'save' method is never executed and mockito always uses mock object that we have created, we can add debug breakpoint inside the 'save' method
          & execute this test method in debug mode. this confirms that the business logic inside the 'createUser' method was tested in isolation without invoking code of its
          dependencies.                                                                                                                                                           */

        //Mockito.when(usersRepository.save(Mockito.any(User.class))).thenReturn(false); if save method returns false, 'createUser' method will throw 'UserServiceException'.
        Mockito.when(usersRepository.save(Mockito.any(User.class))).thenReturn(true);

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
