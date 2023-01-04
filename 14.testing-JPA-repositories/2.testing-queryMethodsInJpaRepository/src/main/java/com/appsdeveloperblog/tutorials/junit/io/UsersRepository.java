package com.appsdeveloperblog.tutorials.junit.io;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends PagingAndSortingRepository<UserEntity, Long> {

    /* Query methods : spring framework will derive SQL query from the method name of these query methods. so for these methods to be able to communicate with a database, we do not
    need to write a sql query, spring framework will read the method name, and it will derive SQL query from the method name. if the method name is complex, then it is possible that
    the method does compile, and it does work, but because it is complex, we might make a mistake and when we start using it, it'll not return expected result. so it's good to test
    the methods like these.*/
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String email);

    UserEntity findByEmailEndingWith(String email);

    //JPQL Query methods :
    @Query("select user from UserEntity user where user.email like %:emailDomain")
    List<UserEntity> finsUsersWithEmailEndsWith(@Param("emailDomain") String emailDomain);

}
