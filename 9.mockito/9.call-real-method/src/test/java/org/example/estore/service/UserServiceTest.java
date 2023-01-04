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

/* how to make mockito call real method instead of stabbing it with predefined value :
   There might be a time when even though we've mocked an object, we still want to call a real method on it. Generally, we will always be able to stub methods of our own mock
   object, especially when testing our own well-designed code. But when working with old and legacy code, or when testing code that needs complex refactoring, we might need to
   make mockito just call a real method instead.                    */
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
        doThrow(EmailNotificationServiceException.class).when(emailVerificationService).scheduleEmailConfirmation(any(User.class));

        //Act & Assert
        assertThrows(UserServiceException.class, () -> {
            //Act
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "should've thrown UserServiceException instead");

        //Assert
        verify(emailVerificationService, times(1)).scheduleEmailConfirmation(any(User.class));
    }

    /* if we debug this test method, we can see that even though the 'emailVerificationService' is a mock object, the real 'scheduleEmailConfirmation' method is called When
    'createUser' method which is Method under Test is executed. debug break point will trigger inside the 'scheduleEmailConfirmation' method, and it proves that mockito did
    call real method, real implementation of 'scheduleEmailConfirmation' method.                                                                                             */
    @DisplayName("Schedule Email Confirmation is executed") @Test void testCreateUser_whenUserCreated_scheduleEmailConfirmation() {

        // Arrange
        //stub 'save' method: when 'usersRepository' mock object is used to invoke 'save' method with any object of 'User' data type, return true. when 'createUser' method under test executed.
        when(usersRepository.save(any(User.class))).thenReturn(true);
        //stub 'scheduleEmailConfirmation' method: when 'emailVerificationService' mock object is used to invoke 'scheduleEmailConfirmation' void method, call real 'scheduleEmailConfirmation' method. when 'createUser' method under test executed.
        doCallRealMethod().when(emailVerificationService).scheduleEmailConfirmation(any(User.class));

        //Act: invoke 'createUser' method under test.
        userService.createUser(firstName, lastName, email, password, repeatPassword);

        //Assert
        verify(emailVerificationService, times(1)).scheduleEmailConfirmation(any(User.class));
    }

}
