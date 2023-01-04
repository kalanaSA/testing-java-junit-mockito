package com.appsdeveloperblog.tutorials.junit.io;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.UUID;

/* create a test method that'll help us test if 'UserEntity' entity object can be persisted in a database table. (create test classes under the same package that main classes exist)
   - TestEntityManager : to persist entity objects into a database table, we'll need to use 'TestEntityManager' object, and to make that object available to our test methods in our
     test class, we'll need to autowired it. this 'TestEntityManager' object is an alternative object to 'EntityManager' and we'll use it in test classes when testing data layer.
     it will allow us to persist information and then synchronize it with the database table right away.                                                                          */

@DataJpaTest /* to test only data layer, isolation from other layers. spring framework will create application context only for those beans that are related to spring data JPA
persistence layer. it will create and put into application context, only entity objects and repository objects.                                                                   */
public class UserEntityIntegrationTest {

    @Autowired
    TestEntityManager testEntityManager;
    UserEntity userEntity;

    @BeforeEach void setUp() {
        //arrange : prepare 'UserEntity' entity object with valid user details.
        userEntity = new UserEntity();
        userEntity.setUserId(UUID.randomUUID().toString());
        userEntity.setFirstName("kalana");
        userEntity.setLastName("sandakelum");
        userEntity.setEmail("kalana@test.com");
        userEntity.setEncryptedPassword("12345678");
    }

    /* test 'UserEntity' entity object with valid user information. so in this case system under test or class under test will be Entity class(UserEntity). if there is an issue
    with our entity object, it will not be persisted in a database, and we'll get an exception and our test method will fail.                                                     */
    @Test
    @DisplayName("User can be persisted")
    void testUserEntity_whenValidUserDetailsProvided_shouldReturnsStoredUserDetails() {
        //act : persist prepared entity object into the database.
        UserEntity storedUser = testEntityManager.persistAndFlush(userEntity); /* to persist 'UserEntity' entity object into the database table, we'll use 'TestEntityManager' object,
        that have made available to our test class/methods, by autowiring it. persistAndFlush() method will return persisted data in the database, as the object of the same source
        entity class, and also it will be updated with generated 'id' value that we've not provided when persisted. so we can validate, if the database id was generated for this
        record? and is present?.                                                            */

        /* assert : validate the returned values. validate all the fields in returned 'storedUser' entity object and make sure that they exactly the same as in the original
        'userEntity' entity object that was persisted into a database table. (both are objects of same 'UserEntity' entity class)                                                 */
        assertTrue(storedUser.getId() > 0); /* assert that the auto increment 'id' field has been assigned to a value. in our test method, we do not set any value for this
        field. This 'id' will be auto generated for us by @GeneratedValue, and also it is going to be a primary key.
        so when our entity object gets persisted into a database table as a row, the very first id that this row will have a value '1'. and when we persist next object, the next
        row will have value '2' and so on. if we had only one test method that stores the User entity, then the value of this 'id' member variable will be equal to '1'. but because
        we're going to have more than one test method in our test class, and because we do not know which method will execute first, we cannot guarantee that the value that this
        getId() method will return will always be '1'. so we'll simply assume that this value is higher than 0.                                                                   */
        assertEquals(userEntity.getUserId(), storedUser.getUserId());
        assertEquals(userEntity.getFirstName(), storedUser.getFirstName());
        assertEquals(userEntity.getLastName(), storedUser.getLastName());
        assertEquals(userEntity.getEmail(), storedUser.getEmail());
        assertEquals(userEntity.getEncryptedPassword(), storedUser.getEncryptedPassword());
    }

}
