package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.util.UUID;

/* Test other properties of our 'UserEntity' entity.
 * 'userId' column is configured in entity class to be unique, and that means the value in the userId column in our 'users' database table also must be unique. there cannot be any
    other record in the 'users' database table that has the same value in userId column.
 * if we try to persist another user with exactly the same userId, we should get an exception message because two users with the same userId cannot exist. And if exception does
   take place, then our test method should pass. Otherwise, if exception does not take place, our test method should fail.                                                        */
@DataJpaTest
public class UserEntityIntegrationTest {

    @Autowired TestEntityManager testEntityManager;
    UserEntity userEntity;

    @BeforeEach void setUp() {
        //arrange
        userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setFirstName("kalana");
        userEntity.setLastName("sandakelum");
        userEntity.setEmail("kalana@test.com");
        userEntity.setEncryptedPassword("12345678");
    }

    //test method that validate userId column accepts only unique values.
    @Test
    @DisplayName("UserId cannot be duplicate")
    void testUserEntity_whenExistingUserIdProvided_shouldThrowException() {
        /* arrange : create and *persist a 'UserEntity' entity object with specific userId. (it is okay to persist user entity in the 'arrange' section because the 'arrange' section
        is used to prepare objects and systems state with information that is needed to test system under test. and this is exactly what we do in here. we prepare and persist one
        user entity in here, so then our system under test is set up, and then we can try to persist another user entity to test the use case we're testing with this test method.  */
        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setUserId("1");
        newUserEntity.setFirstName("ashani");
        newUserEntity.setLastName("sundarawadu");
        newUserEntity.setEmail("ashani@test.com");
        newUserEntity.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(newUserEntity);

        //create another 'UserEntity' entity object with the same userId to be persisted. (updated existing user entity have initially created inside the beforeEach method)
        userEntity.setUserId("1");

        /* assert & act : try to persist another 'UserEntity' entity object with same userId. and expect 'PersistenceException' exception to take place. if exception does not take
        place, then this test method will fail. and it identifies/indicates something is not right with entity class which is class under test. so we can check and fix them to
        align with business requirement. */
        Assertions.assertThrows(PersistenceException.class, () -> {
            //act
            testEntityManager.persistAndFlush(userEntity);
        }, "Expected PersistenceException to be thrown here.");
    }

    @Test
    @DisplayName("User cannot be persisted")
    void testUserEntity_whenFirstNameIsTooLong_shouldThrowException() {
        //arrange
        userEntity.setFirstName("kalanaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        //assert & act
        Assertions.assertThrows(PersistenceException.class, () -> {
            //act
            testEntityManager.persistAndFlush(userEntity);
        }, "was expecting a PersistenceException to be thrown.");
    }

    @Test
    @DisplayName("User can be persisted")
    void testUserEntity_whenValidUserDetailsProvided_shouldReturnsStoredUserDetails() {
        //act
        UserEntity storedUser = testEntityManager.persistAndFlush(userEntity);

        //assert
        Assertions.assertTrue(storedUser.getId() > 0);
        Assertions.assertEquals(userEntity.getUserId(), storedUser.getUserId());
        Assertions.assertEquals(userEntity.getFirstName(), storedUser.getFirstName());
        Assertions.assertEquals(userEntity.getLastName(), storedUser.getLastName());
        Assertions.assertEquals(userEntity.getEmail(), storedUser.getEmail());
        Assertions.assertEquals(userEntity.getEncryptedPassword(), storedUser.getEncryptedPassword());
    }

}
