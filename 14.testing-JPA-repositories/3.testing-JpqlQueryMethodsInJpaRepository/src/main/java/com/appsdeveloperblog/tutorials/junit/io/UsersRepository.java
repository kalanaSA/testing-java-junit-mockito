package com.appsdeveloperblog.tutorials.junit.io;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends PagingAndSortingRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    UserEntity findByUserId(String email);

    UserEntity findByEmailEndingWith(String email);

    /* JPQL Query methods :  these methods uses the query defined above it. and because these methods has a query defined above it, the method name does not need to follow the
    pattern that we used to create query methods. and we can give these methods any name we like.*/
    @Query("select user from UserEntity user where user.email like %:emailDomain")
    List<UserEntity> findUsersWithEmailEndsWith(@Param("emailDomain") String emailDomain);
}
