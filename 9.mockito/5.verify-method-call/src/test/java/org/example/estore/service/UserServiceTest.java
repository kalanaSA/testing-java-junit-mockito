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

/* Every time we call Mockito's static methods, we need to refer 'Mockito' class. So instead of repeating Mockito class, every time we need to call a static method, we can add a
   'static import statement' to class instead. then we can remove 'Mockito' class.                                                                                                */
import static org.mockito.Mockito.*;

/* verify() : How to make sure that the dependency object(mock object) inside the method we're testing(method under test) is used once, more than once, or it was never used at all.
   Mock objects allow us to verify that how many times the methods in the mock objects was called. (verify the number of invocations)
        - In this case, we want to make sure that the 'createUser' method which is method under test, invokes 'save' method exactly one time. If this 'save' method
          was not called at all, or it was called more than one time, then we have a problem. so we want our test method to verify and make sure that this 'save'
          method was called exactly one time. Otherwise, our test method should fail.

          Mockito.verify(MOCK_OBJECT, TIME_VERIFICATION_MODE).METHOD_EXPECTED_TOBE_CALLED(ARGUMENTS)
          MOCK_OBJECT : usersRepository
          VERIFICATION_MODE(desired number of method invocation) : Mockito.times(1) : 1
          ARGUMENTS : Mockito.any(dataType_Or_NameOfTheClass) : save(Mockito.any(User.class)) - 'save' method accepts 'User' object as a parameter. And to provide this parameter
          we can use mockito's argument matcher.                                                                                                                                  */

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

        //Ask mockito to verify that the 'save' method that is in the usersRepository mock object was called exactly one time, Otherwise this test method should fail.
        Mockito.verify(usersRepository, Mockito.times(1)).save(Mockito.any(User.class));
        //when we use mockito verify together with times verification mode, if the value of the wanted number of times is only one, we can actually omit verification mode altogether. because 1 is default value.
        Mockito.verify(usersRepository).save(Mockito.any(User.class));

        //Mockito.verify(usersRepository, Mockito.times(0)).save(Mockito.any(User.class));
        //Mockito.verify(usersRepository, Mockito.times(2)).save(Mockito.any(User.class));

        // other useful methods that allow us to verify the number of invocations.
        Mockito.atLeast(1); //method was called at least the number of times specified as the method argument.
        Mockito.atMost(1);  //method was called not more than the specified number of times specified as the method argument.
        Mockito.atLeastOnce(); //method was called at least one time.
        Mockito.atMostOnce();  //method was called not more than one time.
        Mockito.never(); //method was never called.
    }

}
