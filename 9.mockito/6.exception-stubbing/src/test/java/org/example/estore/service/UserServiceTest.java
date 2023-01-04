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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/* recap: 'createUser' method(method under test) has a dependency on usersRepository. because we want to test the 'createUser' method in isolation from its dependencies
   we have mocked usersRepository object. so the usersRepository is now a mock object.                                                                                               */
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
        // Arrange
        when(usersRepository.save(any(User.class))).thenReturn(true);

        //Act
        User user = userService.createUser(firstName, lastName, email, password, repeatPassword);

        //Assert
        assertNotNull(user, "createUser() shouldn't have returned null");
        assertEquals(firstName, user.getFirstName(), "User's first name is incorrect");
        assertEquals(lastName, user.getLastName(), "User's last name is incorrect");
        assertEquals(email, user.getEmail(), "User's email is incorrect");
        assertNotNull(user.getId(), "User id Shouldn't be null");

        verify(usersRepository, times(1)).save(any(User.class));
    }

    /* when().thenThrow() : mock object exception stubbing(stub mock object's methods with exceptions) :
    In here we're trying to test 'createUser' method which is method under test, returns expected result even when 'save' method is invoked and throws an exception. because
    usersRepository is now a mock object, we can configure it to always throw specific exception when the 'save' method gets executed. so making usersRepository mock object to
    always throw an exception when the 'save' method is invoked is called exception stubbing.
    note: run this test method in a debug mode and check if the UserServiceException is indeed thrown.   (look at UserServiceImpl.createUser() method)                            */
    @DisplayName("If save() method cause RuntimeException, a UserServiceException is thrown") @Test void testCreateUser_whenSaveMethodThrowsException_shouldThrowsUserServiceException() {

        // Arrange: exception stubbing(configured to throw RuntimeException when 'save' method is invoked using mockito/ stubbing 'save' method with an exception)
        when(usersRepository.save(any(User.class))).thenThrow(RuntimeException.class);
        //note: in this project we've not configured to work with any specific database, so cannot fully use any specific database exception here. So for this demonstration, we've made 'save' method throw a general runtime exception.                                                */

        //Act & Assert : invoke method under test(createUser) and assert that it throws 'UserServiceException' or not.
        assertThrows(UserServiceException.class, () -> {
            //Act
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "should've thrown UserServiceException instead"); //if 'createUser' was invoked and 'UserServiceException' was not thrown, we'll see this message in the test report.

        //Assert
        verify(usersRepository, times(1)).save(any(User.class));
    }

}
