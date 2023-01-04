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

/* How to run unit tests and generate regular unit test reports and code coverage reports using Maven:
        - If our project is Maven base project, then we can configure Maven to generate and export regular unit test report or test report containing code coverage information
          as well. Making Maven generate code coverage report is very helpful when you use continuous integration and continuous deployment tools. at those times when we do not
          use development environment to build our project and run unit tests.
 * Configure our MAVEN project to export a regular unit test report in HTML format.(contain information about passed and failed test methods, but it will not contain code coverage
   information).
        - So to configure our MAVEN project to export a regular unit test report, we need to add one additional plugin called 'maven surefire report plugin' to pom.xml file.
          (we use 'maven-surefire-plugin' to run unit tests using maven)
        - So to make maven execute this plugin during the test phase, we need to add to this plugin one additional configuration. and that will go to 'executions' section.
          we'll set phase as 'test' and goal as 'report'.(see pom.xml)
          So with that configuration, we tell Maven that when we run Maven command to execute the 'test' phase, we want this plugin to execute its goal that is called 'Report'.
        - so this configuration should already make Maven generate and export regular unit test report in HTML format, If we execute the test phase. if all unit tests pass, then
          report will be generated. But if at least one unit test fails, then unfortunately the report will not be generated. And to make maven export test report, even though
          there is a failing test, we'll need to add one more configuration. But this one will need to go into 'maven surefire plugin' itself.
        - 'mvn test'/ 'mvn clean test'
          clear: clear the 'target' directory with all its previously compiled classes and generated reports first & then run the test phase.
          test: test option here will tell us when to execute test phase. And during the test phase, we'll have our code compiled and our unit tests will be executed.
        - unit test report will be generated in 'target->site->surefire-report.html'
        - 'mvn site -DgenerateReports=false' - way to make this test report look just a little nicer(add some images and CSS styling to this HTML report)

 * Configure our MAVEN project to export code coverage report in HTML format.
       - to make MAVEN generate and export code coverage report, We will need to add to our project one more plugin called 'jacoco-maven-plugin'.
       - 'Jacoco' stands for Java code coverage, and it will help us export code coverage report in an HTML format when we execute our unit tests using MAVEN.
       - after adding 'jacoco' plugin into pom.xml, there are a couple of configurations that we need to make. and those are go to 'executions' section.
         So this first 'execution' is needed to do some initialization for jacoco agent.
         the second 'execution' that we are going to add will make jacoco, executes its 'report' goal during maven 'test' phase execution.(see pom.xml)
       - To execute the jacoco 'Report' goal, we'll need to execute Maven's test phase : mvn test/ mvn clean test
       - test report with code coverage will be in 'target->site->jacoco->index.html'
       - details about jacoco code coverage report columns:
            * missed branched: are the if conditions in our code and switch statements, for example.
            * Cxty: psychometric complexity is a software a metric that is used to indicate the complexity of our code. If our source code contains no control flow statements,
              then its psychosomatic complexity will be just one. But if our method contains at least one if condition for example, then the psychometric complexity will already
              be equal to two, because there are two possible ways the execution can go. So the more if statements or switch statements you have in your code, the higher is the
              value of psychosomatic complexity.


 * */
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
