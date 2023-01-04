package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.UUID;

//Test query methods that we've created in our JPA repositories works well. repositories belong to the data layer, and we're testing only data layer isolation from other layers.

@DataJpaTest //because we're testing only JPA layer of our application, we need to annotate this test class with the @DataJpaTest annotation.
public class UsersRepositoryTest {

    //autowired repository object to the test class, to invoke query methods in the repository which are method under tests to be tested.
    @Autowired UsersRepository usersRepository;
    @Autowired TestEntityManager testEntityManager;

    private final String email1 = "test1@test.com";
    private final String email2 = "test2@test.com";
    private final String userId1 = UUID.randomUUID().toString();
    private final String userId2 = UUID.randomUUID().toString();

    /* setup method to create and persist 'UserEntity' entity objects into a database. prepare objects that needs to perform test methods. in order to test query methods works well
    or not, there should be already one or more records in the database.                                                                                                        */
    @BeforeEach
    void setUp() {
        // Arrange
        //first user
        UserEntity user1 = new UserEntity();
        user1.setUserId(userId1);
        user1.setFirstName("kalansa");
        user1.setLastName("sandakelum");
        user1.setEmail(email1);
        user1.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(user1); //to persist entity objects into a database table, we need to use 'TestEntityManager'.

        //second user
        UserEntity user2 = new UserEntity();
        user2.setUserId(userId2);
        user2.setFirstName("ashani");
        user2.setLastName("sundarawadu");
        user2.setEmail(email2);
        user2.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(user2);
    }

    //test a query method in a jpa repository that tries to find an existing record in a database, can actually find the record or not.
    @Test
    @DisplayName("user can be find using email")
    void testFindByEmail_whenGivenCorrectEmail_returnsUserEntity() {
        //act : invoke query method in the repository which is method under test to perform test.
        UserEntity storedUser = usersRepository.findByEmail(email1);

        //assert : validate that returned entity object does contain details that we expected.
        Assertions.assertEquals(email1, storedUser.getEmail(), "returned email address does not match the expected value");
    }


    @Test
    @DisplayName("user can be find using userId")
    void testFindByUserId_whenGivenCorrectUserId_returnsUserEntity() {
        /* act : use 'usersRepository' object to invoke method under test which is 'findByUserId()'. and as a parameter we give it userId of a second user that we've persisted into
        a database table. and if the jpa query method that we've created works well, and if there is a user with this userId found in the database table, then in response back we
        should get a 'UserEntity' entity object containing details of our second user.                                                                                          */
        UserEntity storedUser = usersRepository.findByUserId(userId2);
        //UserEntity storedUser = usersRepository.findByUserId("abc");  user doesn't exist
        //UserEntity storedUser = usersRepository.findByUserId(userId1); user exist, but it doesn't have a 'userId' that we're looking for.

        // assert : make sure that the received 'UserEntity' entity object which is 'storedUser' object is not null, and  contains expected 'userId'.
        Assertions.assertNotNull(storedUser, "UserEntity object should not be null");
        Assertions.assertEquals(userId2, storedUser.getUserId(), "returned userId does not match the expected value");
    }

}
