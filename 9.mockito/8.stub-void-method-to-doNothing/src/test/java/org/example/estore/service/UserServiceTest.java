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

/* mockito stubs we've used so far:
    when().thenReturn();
    when().thenThrow();
    doThrow().when().VOID_METHOD(ARGUMENTS);

 * doNothing().when().VOID_METHOD(ARGUMENTS) : another useful mockito stub that used to make 'void' method do nothing when it is invoked.
 * use cases: if we've stubbed our void method to throw an exception, in one of the lifecycle setup methods for example, and if we do not want this behavior in other
   test methods, then we can use doNothing() to explicitly tell mockito that we do not want to invoke this method(mock object's method) if it is called.                          */
@ExtendWith(MockitoExtension.class) public class UserServiceTest {

    @Mock UsersRepository usersRepository;

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

        // Arrange:
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

        // Arrange
        when(usersRepository.save(any(User.class))).thenReturn(true);
        //1. we've configured our 'emailVerificationService' mock object to throw 'EmailNotificationServiceException' when 'scheduleEmailConfirmation' method is called.
        doThrow(EmailNotificationServiceException.class).when(emailVerificationService).scheduleEmailConfirmation(any(User.class));

        /* 2. If we use doNothing() right after it, then it will overwrite above configured behavior and will make this 'scheduleEmailConfirmation' method do nothing when it is
        called. then below assertion and test will fail because when 'scheduleEmailConfirmation' method invoked it'll do nothing instead of throwing exception that we're expecting.
        do nothing when 'emailVerificationService' mock object is used to invoke 'scheduleEmailConfirmation' method with any object of 'User' data type.                        */
        doNothing().when(emailVerificationService).scheduleEmailConfirmation(any(User.class));

        //Act & Assert
        assertThrows(UserServiceException.class, () -> {
            //Act
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "should've thrown UserServiceException instead");

        //Assert
        verify(emailVerificationService, times(1)).scheduleEmailConfirmation(any(User.class));
    }

}
