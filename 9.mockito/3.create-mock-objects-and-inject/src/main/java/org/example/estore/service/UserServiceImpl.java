package org.example.estore.service;

import org.example.estore.data.UsersRepository;
import org.example.estore.model.User;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    /* way 2: constructor base dependency injection. inject 'UsersRepository' as a dependency to 'UserService' class. so define a new instance of 'UsersRepository' On a class
    level, it's going to be member variable and create a constructor(constructor for this class that will initialize UsersRepository property) to initialize that. this way, when
    creating a new instance of 'UserService' implementation class, we can pass here and object of any class as long as it implements 'UsersRepository' interface. And we'll use
    mockito to create a mock object that implements this interface for us automatically.                                                                                       */

    UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override public User createUser(String firstName, String lastName, String email, String password, String repeatPassword) {

        if (firstName == null || firstName.trim().length() == 0) {
            throw new IllegalArgumentException("User's first name is empty");
        }

        if (lastName == null || lastName.trim().length() == 0) {
            throw new IllegalArgumentException("User's last name is empty");
        }

        User user = new User(firstName, lastName, email, UUID.randomUUID().toString());

        /* Mockito helps us to create fake and mock objects to let us test our method under test in isolation from its dependencies.
           dependency: in this 'createUser' method, data access object or may be repository object(UsersRepository) that use to save data in database is a dependency.            */

        /* there are different ways how we can make an object of 'UsersRepository' class available in our 'userService' implementation.
           way 1 : create an 'UsersRepository' object with right a way inside the 'createUser' method.
           UsersRepository usersRepository = new UsersRepositoryImpl();
           But this will be incorrect because in this case, if we called the 'createUser' method from our junit test method, the code inside this 'save' method will be executed,
           and user will actually be persisted to our storage. And in this case is going to be not unit test anymore, but integration test.When writing the unit test to test this
           'createUser' method, which is our method under test, we need to isolate this method from all its dependencies because we are not actually testing code inside 'save'
           method, and we do not want to invoke a real save method. So instead of using a real UserRepository object, we want to replace this real object(new UsersRepositoryImpl())
           with a mock object. But if we create an instance of UsersRepository implementation this way, then there is no way we can make 'createUser' method use a mock object
           instead of a real one. So we need to use dependency injection and instead of creating a new object of UsersRepository implementation this way, we will need to inject it
           using constructor based dependency injection.                                                                                                                                                     */
        boolean isUserCreated = usersRepository.save(user);

        if (!isUserCreated)
            //CUSTOM exception throwing from the 'createUser' method if user was not successfully created.
            throw new UserServiceException("could not create user");

        return user;
    }
}
