package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.PersistenceException;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;

/* Test if we can persist entity object with invalid information : test the 'JPA entity', for a use case if invalid values provided for 'JPA Entity' object, it'll throw a
   'PersistenceException'. this is because values will store into the database columns, accordingly as we have configured in the 'JPA entity' class.                    */

@DataJpaTest
public class UserEntityIntegrationTest {

    @Autowired TestEntityManager testEntityManager;
    UserEntity userEntity;

    @BeforeEach void setUp() {
        /* arrange : because these user details are almost exactly same for the most of the test methods, we can simply add them here in the @BeforeEach lifecycle method. only
        values that needs to be changed to test specific scenario, can be override inside that test method(arrange section).                                                */
        userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setFirstName("kalana");
        userEntity.setLastName("sandakelum");
        userEntity.setEmail("kalana@test.com");
        userEntity.setEncryptedPassword("12345678");
    }

    //2. test method with invalid user details : test method to test how our entity object will work if we provide invalid value for one of these fields.
    @Test
    @DisplayName("User cannot be persisted")
    void testUserEntity_whenFirstNameIsTooLong_shouldThrowException() {
        //arrange : set invalid value for users first name(set longer than the allowed value, we've configured in 'UserEntity' entity for the firstName column).
        userEntity.setFirstName("kalanaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        /* assert & act : assert that when we try to persist 'UserEntity' entity object (into the db) with invalid values, it'll throw 'PersistenceException'. data type of the
        exception that we'll expect back will be PersistenceException in here. */
        Assertions.assertThrows(PersistenceException.class, () -> {
            //act : try to persist 'UserEntity' entity object with invalid values.
            testEntityManager.persistAndFlush(userEntity);
        }, "was expecting a PersistenceException to be thrown."); //if expected exception does not take place, this error msg will be shown.
    }

    //1. test method with valid user details.
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
