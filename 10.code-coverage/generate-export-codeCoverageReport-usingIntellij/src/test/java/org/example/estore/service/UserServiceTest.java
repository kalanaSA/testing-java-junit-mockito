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

/* How to generate code coverage reports using Intellij IDE, when we run unit tests:
    - code coverage report tells us how much of our application code we've been covered with unit tests. for example, how many classes, methods, lines were invoked when we run our
      unit tests. (how many methods and how many lines of those methods in our classes were engaged when we run unit tests)
    - It can help us find methods that we still need to test(methods in our application that have not been covered with unit tests). because if a method is not covered with unit
      tests, then there is a chance that method might have a bug. It was not tested, So possibly it might not work as expected.
    - according to the code coverage report, if there is a method or code lines in one of our class that was not covered when we run unit tests, that means that method was not
      invoked(not tested with unit tests). So we might want to open that method, review it, and then write a unit test for it.
    - when there is good code coverage, we have a higher confidence that our application is working as we expected.
    - but 100% code coverage does not mean that our code is 100% bug free. there can still be bugs in our code. 100% code coverage simply means that 100% of our code was executed
      when we run our unit tests. But it is still possible that if we pass an invalid parameter to one of our methods, then this parameter will cause an exception and our application
      won't behave as expected. And this is why, even though we've 100% code coverage, we still need to double-check that we have tested our method with both valid and invalid
      parameters, that we've thought through negative scenarios, different edge cases. and for each possible each case we've to create a separate unit test. also in a real world
      application 100% code coverage is very challenging to achieve. Most development teams are okay if their code coverage is at about 70 to 80%.
    - ways to run unit tests and generate code coverage report(organize by package) for it.
        * right mouse click in the open test class and then choose 'run <test-class-name> with coverage' to run unit tests in that test class and to generate code coverage report
          only for that particular test class(refer img_1). To run a unit test method and generate code coverage report only for that particular test method in a test class, we can
          right mouse inside a specific test method and choose 'run <test-method-name> with coverage'.(refer img_2)
        * right mouse click a particular test class in the test directory and then choose 'run <test-class-name> with coverage' to run unit tests in that test class and to generate
          code coverage report for only that particular test class(refer img_1). To run all test classes, right mouse click test->java directory and choose 'run all tests with
          coverage'.
        * using toolbar icon.(choose the test class and click icon to run all the unit tests in that test class with coverage)
        * classes that were not involved during the test method execution. They do not have the code coverage report generated. ex: UsersRepositoryImpl, User classes.
        * on the left side panel, we can see green bar indicator for those line numbers that were executed(covered by unit tests) during unit test execution. And if there are lines
          of code that were not executed(not covered by unit tests), then there will be a red bar indicator. (refer img_3)
    - how to export this test coverage report in HTML format. so that we can share it with our team members or even publish on corporate intranet for others to see.
        * run unit tests with code coverage and then click the export icon which says 'Generate code coverage report/export test results' either in the test result report panel or
          test coverage panel. (see 'intellij-test-report' in test directory)                                                                                                     */
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

    @DisplayName("If firstName null or empty, a IllegalArgumentException is thrown") @Test void testCreateUser_whenFirstNameNullOrEmpty_shouldThrowsIllegalArgumentException() {
        //Arrange
        firstName = "";
        String expectedExceptionMsg = "User's first name is empty";

        //Act & Assert
        IllegalArgumentException actualException = assertThrows(IllegalArgumentException.class, () -> {
            //Act
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "should've thrown IllegalArgumentException instead");

        //Assert
        assertEquals(expectedExceptionMsg, actualException.getMessage());
    }

    @DisplayName("If lastName null or empty, a IllegalArgumentException is thrown") @Test void testCreateUser_whenLastNameNullOrEmpty_shouldThrowsIllegalArgumentException() {
        //Arrange
        lastName = "";
        String expectedExceptionMsg = "User's last name is empty";

        //Act & Assert
        IllegalArgumentException actualException = assertThrows(IllegalArgumentException.class, () -> {
            //Act
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "should've thrown IllegalArgumentException instead");

        //Assert
        assertEquals(expectedExceptionMsg, actualException.getMessage());
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

    @DisplayName("If save() method returns false, a UserServiceException is thrown") @Test void testCreateUser_whenSaveMethodReturnsFalse_shouldThrowsUserServiceException() {
        // Arrange:
        String expectedExceptionMsg = "could not create user";
        when(usersRepository.save(any(User.class))).thenReturn(false);

        //Act & Assert
        UserServiceException actualException = assertThrows(UserServiceException.class, () -> {
            //Act
            userService.createUser(firstName, lastName, email, password, repeatPassword);
        }, "should've thrown UserServiceException instead");

        //Assert
        assertEquals(expectedExceptionMsg, actualException.getMessage());
        verify(usersRepository, times(1)).save(any(User.class));
    }

    @DisplayName("If scheduleEmailConfirmation() method cause EmailVerificationException, a UserServiceException is thrown") @Test void testCreateUser_whenScheduleEmailConfirmationThrowsException_shouldThrowsUserServiceException() {
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

    @DisplayName("Schedule Email Confirmation is executed") @Test void testCreateUser_whenUserCreated_scheduleEmailConfirmation() {
        // Arrange
        when(usersRepository.save(any(User.class))).thenReturn(true);
        doCallRealMethod().when(emailVerificationService).scheduleEmailConfirmation(any(User.class));

        //Act
        userService.createUser(firstName, lastName, email, password, repeatPassword);

        //Assert
        verify(emailVerificationService, times(1)).scheduleEmailConfirmation(any(User.class));
    }

}
