package com.appsdeveloperblog.tutorials.junit.io;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/* How to test the data layer of our springboot application :
    - spring framework allows us to test each layer of our springboot application separately. if needed we can test web layer of our application separately from service layer and
      data layer, we can test service layer in isolation from data layer, and we can test data layer of our application separately from web layer and service layer.
      in here we're going to write integration tests to test data layer of our springboot application separately from other layers.
    - When it comes to data layer, there are two components that we are going to test. (refer img_1)
        1. JPA Entities.
        2. JPA Repositories.

 1. JPA Entities : in this section we'll write integration test to test JPA entities of our data layer isolation from other layers.
    - What is JPA Entity? JPA Entity Object will describe information that we need to store in our database. For example, if we need to store user details, then we'll create a new
      User entity object and this object will contain user details like user's first name, last name, email and password etc. and we can think of this object as a regular Java
      bean object with member variables and getters/setters methods. but additionally to contain user details, this object will also contain information about 'database table schema'.
    - It is not often that we'll see developers write integration tests for 'JPA entities'. many developers feel that testing 'JPA repositories' is sufficient enough, because if
      JPA entity has issues, then most likely these issues will be caught during the 'JPA repository' test. but if needed, we can still test 'JPA entities' as well.
    - what is it that we're going to test in JPA entity?
        * refer example of JPA entity class in img_02. we can notice that this JPA entity class has a member variables annotated with some specific annotations. for example, the id
        field is annotated with @Id annotation and @GeneratedValue annotation and other fields are annotated with @Column annotation. and additionally to this column annotation, it
        has properties specified. for example, userId value cannot be null and must be unique, and maximum lengths of the value that the first name column can have is 50 characters,
        and the value of email address that each user record can have in the database table must also be unique. like that.
        So let's assume that we've this 'UserEntity' class in our application. at the time when our Springboot application starts up, spring framework will scan packages of our
        application looking for classes with this kind of special annotations and depending on what annotations are used in the class, spring framework will process this class
        accordingly. for example, for this 'UserEntity' class spring framework will check if there is a database table with the name 'users' exist and this is because this class is
        annotated with @Table annotation and its name is 'users'. if our database does not have a table called 'users', then Spring Framework will attempt to create this table
        using the information specified in the @Column annotations. so for this entity class, spring framework will create a new database table that is called 'users', and this
        database table will have columns called id, userid, firstName, lastName, email and encryptedPassword. each database table column will be created accordingly to information
        specified in the @Column annotation. (refer img_2)
        * so as a developer, works on a data layer in isolation from controllers or service classes, we need to make sure that the 'data layer is created according to business
        requirements'. so what we can do is we can write test methods that validate these business requirements. for example, one of the test methods that we can write for this
        'JPA entity' is to validate that our database table does not allow duplicate values for 'userId', and if we try to persist User entity object with already existing userId,
        then we should get back an exception. and another test method we can write this to validate that we cannot persist User entity object with duplicate email address. so there
        are things that we can test in a JPA entity before we start working on a repository.
 2. JPA Repositories : next section.

 * @DataJpaTest : To test JPA entity objects or JPA repositories, we'll need to annotate our class with @DataJpaTest annotation. there are a few important details about this
   annotation. (refer img_3)
      - this annotation will disable autoconfiguration, and it will make spring framework create application context only for JPA related components. because we're testing only
        data layer, spring framework will create application context with only JPA related beans.
      - also, this annotation will make our test methods transactional, and once our test method completes, all changes that were made to our database table will roll back and
        will be undone.
      - and another important detail is that we do not need to have database server running and configure database connection to make our test method work. by default our test
        methods will work with embedded in memory database. if needed we can of course use separate database server, but by default our test methods will use embedded in-memory
        database, and we do not need to make any additional configuration for it to work.

                                                                                                                                                */

@DataJpaTest
public class UserEntityIntegrationTest {
}
