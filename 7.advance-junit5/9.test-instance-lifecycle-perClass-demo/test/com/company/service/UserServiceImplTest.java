package com.company.service;

import com.company.io.UsersDatabase;
import com.company.io.UsersDatabaseMapImpl;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

/* how to make our test methods execute in order and if needed, make them in a single instance of a test class:
 * in here test methods has been ordered because each test method depend on execution of another test method and also marked this test class as test instance per class because
   to be able to access the same class instance variables.
   (some test methods depend on the values of instance variables(ex:createdUserId), that will add/change by another test method when it executes)
 * this can be achieved by using test method ordering & using test-instance-per-class.
 * all test methods in this class will need to run in a single instance of a test class or everything fails.
 * note: Ideally, we should create additional test methods and test other details as well.                                                                                        */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) @TestInstance(TestInstance.Lifecycle.PER_CLASS) public class UserServiceImplTest {

    UsersDatabase usersDatabase;
    UserService userService;
    String createdUserId = "";

    //because there is going to be only one single instance of this class, before all and after all lifecycle test methods are not static.

    @BeforeAll void setup() {
        //good place to create a temporary database,and establish a connection with that database.
        // Create & initialize database
        usersDatabase = new UsersDatabaseMapImpl();
        usersDatabase.init();
        userService = new UserServiceImpl(usersDatabase);
    }

    @AfterAll void cleanup() {
        // Close connection/Delete database
        usersDatabase.close();
    }

    @Test @Order(1) @DisplayName("Create User works") void testCreateUser_whenProvidedWithValidDetails_returnsUserId() {
        //Arrange
        Map<String, String> user = new HashMap<>();
        user.put("firstName", "Kalana");
        user.put("lastName", "Sandakelum");
        //Act
        createdUserId = userService.createUser(user);
        //Assert
        assertNotNull(createdUserId, "User Id should not be null");
    }

    /* second test method depends on the first test method, and it will not pass if we run it alone. first & second test methods share the same instance of test class
    because value of the 'createdUserId' instance variable which use in here, but it's value will add by first method.   */
    @Test @Order(2) @DisplayName("Update user works") void testUpdateUser_whenProvidedWithValidDetails_returnsUpdatedUserDetails() {
        //Arrange
        Map<String, String> newUserDetails = new HashMap<>();
        newUserDetails.put("firstName", "micheal");
        newUserDetails.put("lastName", "sebastian");
        //Act
        Map updatedUser = userService.updateUser(createdUserId, newUserDetails);
        //Assert
        assertEquals(newUserDetails.get("firstName"), updatedUser.get("firstName"), "Returned value of user's first name is incorrect");
        assertEquals(newUserDetails.get("lastName"), updatedUser.get("lastName"), "Returned value of user's last name is incorrect");
    }

    //this test will only pass the if the first test successful.
    @Test @Order(3) @DisplayName("Find user works") void testGetUserDetails_whenProvidedWithValidUserId_returnsUserDetails() {
        //Act
        Map userDetails = userService.getUserDetails(createdUserId);
        //Assert
        assertNotNull(userDetails, "User details should not be null");
        assertEquals(createdUserId, userDetails.get("userId"), "rerturned user details contain incorrect user id");
    }

    //If this runs first, second or third, it will make all the test methods fail.
    @Test @Order(4) @DisplayName("Delete user works") void testDeleteUser_whenProvidedWithValidUserId_returnsUserDetails() {
        //Act
        userService.deleteUser(createdUserId);

        //Assert
        assertNull(userService.getUserDetails(createdUserId), "user should not been found");

    }

}
