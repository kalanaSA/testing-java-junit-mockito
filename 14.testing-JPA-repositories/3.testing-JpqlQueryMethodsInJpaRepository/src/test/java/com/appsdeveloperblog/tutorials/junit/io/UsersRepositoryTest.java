package com.appsdeveloperblog.tutorials.junit.io;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.UUID;

//Test if the JPQL query method that we've created in the JPA repository works well. (to validate query that we've provided above the method name does work)

@DataJpaTest
public class UsersRepositoryTest {

    @Autowired UsersRepository usersRepository;
    @Autowired TestEntityManager testEntityManager;

    private final String email1 = "test1@test.com";
    private final String email2 = "test2@test.com";
    private final String userId1 = UUID.randomUUID().toString();
    private final String userId2 = UUID.randomUUID().toString();

    //create & persist 2 users. when the time test methods executes, our database table will already have 2 users recorded.
    @BeforeEach
    void setUp() {
        //creating first user
        UserEntity user1 = new UserEntity();
        user1.setUserId(userId1);
        user1.setFirstName("kalana");
        user1.setLastName("sandakelum");
        user1.setEmail(email1);
        user1.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(user1);

        //creating second user
        UserEntity user2 = new UserEntity();
        user2.setUserId(userId2);
        user2.setFirstName("ashani");
        user2.setLastName("sundarawadu");
        user2.setEmail(email2);
        user2.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(user2);
    }

    @Test
    @DisplayName("users can be fetch using email ends with target domain name")
    void testFindUsersWithEmailEndsWith_whenGiveEmailDomain_returnsUsersWithGivenDomain() {
        /* arrange : create and persist another user that have email address ends with different domain. to make this test method more accurate, we can persist one more user in
        the 'arrange' section of this test method.                                                                                                                            */
        UserEntity user3 = new UserEntity();
        user3.setUserId(UUID.randomUUID().toString());
        user3.setFirstName("limesha");
        user3.setLastName("wanniarachchi");
        user3.setEmail("lime@gmail.com"); //define email address ends with different domain.
        user3.setEncryptedPassword("12345678");
        testEntityManager.persistAndFlush(user3);

        //define a variable that will hold the target email domain name.
        String emailDomainName = "@gmail.com";
        //String emailDomainName = "@live.com"; fail, no users
        //String emailDomainName = "@test.com"; fail, more than one user

        //act : invoke jpql query method which is method under test and use above defined target 'emailDomainName' to query for users that use that email address.
        List<UserEntity> userList = usersRepository.findUsersWithEmailEndsWith(emailDomainName);

        //assert : validate result
        //because we've persisted only one user that uses '@gmail.com', we can expect there to be only one user in the list.
        Assertions.assertEquals(1, userList.size(), "there should be only one user in the list");
        //check if the user that is in the list does indeed use email address that ends with the target domain name.
        Assertions.assertTrue(userList.get(0).getEmail().endsWith(emailDomainName), "there should be only one user in the list");
    }

    @Test
    @DisplayName("user can be find using email")
    void testFindByEmail_whenGivenCorrectEmail_returnsUserEntity() {
        //act
        UserEntity storedUser = usersRepository.findByEmail(email1);

        //assert
        Assertions.assertEquals(email1, storedUser.getEmail(), "returned email address does not match the expected value");
    }

    @Test
    @DisplayName("user can be find using userId")
    void testFindByUserId_whenGivenCorrectUserId_returnsUserEntity() {
        //act
        UserEntity storedUser = usersRepository.findByUserId(userId2);

        //assert
        Assertions.assertNotNull(storedUser, "UserEntity object should not be null");
        Assertions.assertEquals(userId2, storedUser.getUserId(), "returned userId does not match the expected value");
    }

}
