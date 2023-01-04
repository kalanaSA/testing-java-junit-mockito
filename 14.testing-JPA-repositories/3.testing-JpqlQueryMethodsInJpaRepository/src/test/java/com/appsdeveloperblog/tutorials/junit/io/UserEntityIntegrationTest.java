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

import static org.junit.jupiter.api.Assertions.assertThrows;

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

    //positive scenarios
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

    //negative scenarios
    @Test
    @DisplayName("UserId cannot be duplicate")
    void testUserEntity_whenExistingUserIdProvided_shouldThrowException() {
        // Arrange: Create and Persist a new User Entity
        UserEntity newEntity = new UserEntity();
        newEntity.setUserId("1");
        newEntity.setEmail("test2@test.com");
        newEntity.setFirstName("test");
        newEntity.setLastName("test");
        newEntity.setEncryptedPassword("test");
        testEntityManager.persistAndFlush(newEntity);

        // Update existing user entity with the same user id
        userEntity.setUserId("1");

        // Act & Assert
        assertThrows(PersistenceException.class, ()-> {
            testEntityManager.persistAndFlush(userEntity);
        }, "Expected PersistenceException to be thrown here");
    }

    @Test
    @DisplayName("User first name is too long")
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
    @DisplayName("User last name is too long")
    void testUserEntity_whenLastNameIsTooLong_shouldThrowException() {
        //arrange
        userEntity.setLastName("sandakalummmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");

        //assert & act
        Assertions.assertThrows(PersistenceException.class, () -> {
            //act
            testEntityManager.persistAndFlush(userEntity);
        }, "was expecting a PersistenceException to be thrown.");
    }

    @Test
    @DisplayName("User eamil is too long")
    void testUserEntity_whenEmailIsTooLong_shouldThrowException() {
        //arrange
        userEntity.setEmail("kalana@test.commmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");

        //assert & act
        Assertions.assertThrows(PersistenceException.class, () -> {
            //act
            testEntityManager.persistAndFlush(userEntity);
        }, "was expecting a PersistenceException to be thrown.");
    }

    @Test
    @DisplayName("user id is null")
    void testUserEntity_whenUserIdNull_shouldThrowException() {
        //arrange
        userEntity.setUserId(null);

        //assert & act
        Assertions.assertThrows(PersistenceException.class, () -> {
            //act
            testEntityManager.persistAndFlush(userEntity);
        }, "was expecting a PersistenceException to be thrown.");
    }

    @Test
    @DisplayName("first name is null")
    void testUserEntity_whenFirstNameIsNull_shouldThrowException() {
        //arrange
        userEntity.setFirstName(null);

        //assert & act
        Assertions.assertThrows(PersistenceException.class, () -> {
            //act
            testEntityManager.persistAndFlush(userEntity);
        }, "was expecting a PersistenceException to be thrown.");
    }

    @Test
    @DisplayName("last name is null")
    void testUserEntity_whenLastNameIsNull_shouldThrowException() {
        //arrange
        userEntity.setLastName(null);

        //assert & act
        Assertions.assertThrows(PersistenceException.class, () -> {
            //act
            testEntityManager.persistAndFlush(userEntity);
        }, "was expecting a PersistenceException to be thrown.");
    }

    @Test
    @DisplayName("email is null")
    void testUserEntity_whenEmailIsNull_shouldThrowException() {
        //arrange
        userEntity.setEmail(null);

        //assert & act
        Assertions.assertThrows(PersistenceException.class, () -> {
            //act
            testEntityManager.persistAndFlush(userEntity);
        }, "was expecting a PersistenceException to be thrown.");
    }

    @Test
    @DisplayName("encrypted password is null")
    void testUserEntity_whenEncryptedPasswordIsNull_shouldThrowException() {
        //arrange
        userEntity.setEncryptedPassword(null);

        //assert & act
        Assertions.assertThrows(PersistenceException.class, () -> {
            //act
            testEntityManager.persistAndFlush(userEntity);
        }, "was expecting a PersistenceException to be thrown.");
    }

}
