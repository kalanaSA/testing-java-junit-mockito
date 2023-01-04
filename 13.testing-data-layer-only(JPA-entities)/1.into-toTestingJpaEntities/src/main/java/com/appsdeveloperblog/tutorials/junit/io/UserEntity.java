package com.appsdeveloperblog.tutorials.junit.io;

import javax.persistence.*;
import java.io.Serializable;

/* This class is annotated with some (JPA) persistence annotations. JPA : Java-Persistence-Api
    - @Entity : this class have annotated with @Entity annotation which will mark this class as persistent entity. that means that we'll be able to use 'spring data JPA' framework
      to persist an object of this class into a database table. so how it will work is when we start our springboot application, spring Framework will scan all classes looking for
      classes with different annotations. and once it finds a class with annotations, depending on what annotations this class is using, spring framework will know how to deal with
      them. it will create an object of this class, and it will place this object as a bean into spring application context. so, when it finds class that is annotated with @Entity
      annotation, it will process that class and will treat it as a persistent entity.
    - @Table :  this class is also annotated with @Table annotation with a name 'users', and this means that the framework will create a database table called 'users' for us. we do
      not need to create this database table manually by running SQL queries, it will be created for us by the framework at the time when our application starts up. so we can think
      like this, Entity as a java class that describes database table, and each property in this class is a database column.
    - @Column : each property in this class is also annotated with @Column annotation, and this means that when 'users' table is created for us, it will be created with these columns.
      it will have a column userId, firstName, lastName, email and so on. @Column annotations also accepts some additional parameters. for example, we specified that the value of
      user's first name cannot be null, and it cannot be longer than 50 characters.
  * once this entity object is persisted, we'll get a new row recorded in a database table. so each entity object that we persist gets recorded as a new row in a database table.
  * what we do is we write test methods to validate that these fields are configured according to the business requirement correctly and that if we try to persist an entity object
    that contains an invalid value, then we'll get an error message.                                                                                                              */
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 5313493413859894403L;

    //this column will be a value which will be auto generated for us. it will be a primary key with a value that will be auto generated.
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 120)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    //getters & setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEncryptedPassword() {
        return encryptedPassword;
    }
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

}
