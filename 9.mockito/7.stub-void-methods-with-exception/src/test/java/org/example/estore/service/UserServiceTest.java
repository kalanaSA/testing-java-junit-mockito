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

/* How to stub void methods | doThrow().when() : how to use mock object to stub void method with an exception.
   To stub 'save' method  with exception we've used 'when().thenThrow()' method. Now if this 'save' method was a void method, then this line would not even compile. to stub void
   methods we'll need to use slightly different approach.                                                                                                                           */
@ExtendWith(MockitoExtension.class) public class UserServiceTest {

    @Mock UsersRepository usersRepository;

    // created mock object of 'EmailVerificationService' and will be injected when an instance of 'UserServiceImpl' is created.
    @Mock EmailVerificationServiceImpl emailVerificationService;
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

    @DisplayName("If save() method cause RuntimeException, a UserServiceException is thrown") @Test void testCreateUser_whenSaveMethodThrowsException_shouldThrowsUserServiceException() {

        //2. Arrange: stub mock object's method with exception | stub 'save' method  with exception.
        when(usersRepository.save(any(User.class))).thenThrow(RuntimeException.class);

        //Act & Assert
        assertThrows(UserServiceException.class, () -> {
            //Act
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "should've thrown UserServiceException instead");

        //Assert
        verify(usersRepository, times(1)).save(any(User.class));
    }

    @DisplayName("EmailVerificationException is handled") @Test void testCreateUser_whenEmailNotificationExceptionThrown_shouldThrowsUserServiceException() {

        //1. Arrange: stub mock object's method | stub 'save' method.
        //When 'usersRepository' mock object is used to call 'save' method to persist any object of 'User' class, then 'save' method should return boolean value True.
        when(usersRepository.save(any(User.class))).thenReturn(true);

        //if we use above same approaches(1,2) to stub void methods, then it will not compile. because when().thenThrow() , when().thenReturn() expressions do not work with void methods.
        //when(emailVerificationService.scheduleEmailConfirmation(any(User.class))).thenThrow(RuntimeException.class);

        /* doThrow().when() - stub mock object's void method  with exception | to stub the 'scheduleEmailConfirmation' void method with exception.
        doThrow(EXCEPTION_WE_WANT_TOBE_THROWN).when(MOCK_OBJECT).VOID_METHOD(ARGUMENTS);
        throw 'EmailNotificationServiceException' when 'emailVerificationService' mock object is used to invoke 'scheduleEmailConfirmation' void method with any object of 'User' class.
        so now if 'scheduleEmailConfirmation' method is invoked, it should throw an exception, and then we can verify if our method under test(createUser) can successfully handle
        that exception and throw different exception that we need.                                                                                                                  */
        doThrow(EmailNotificationServiceException.class).when(emailVerificationService).scheduleEmailConfirmation(any(User.class));

        //Act & Assert:::
        assertThrows(UserServiceException.class, () -> {
            //Act
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "should've thrown UserServiceException instead");

        //Assert:::::::::
        verify(emailVerificationService, times(1)).scheduleEmailConfirmation(any(User.class));
    }

}
