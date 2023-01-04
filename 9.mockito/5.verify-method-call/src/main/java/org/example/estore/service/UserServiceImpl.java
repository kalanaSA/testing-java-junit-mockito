package org.example.estore.service;

import org.example.estore.data.UsersRepository;
import org.example.estore.model.User;

import java.util.UUID;

public class UserServiceImpl implements UserService {

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

        boolean isUserCreated = usersRepository.save(user);
        // if 'save' method never caught(Wanted but not invoked) or if it invoked more than one time(TooManyActualInvocations) when we had called 'save' method more than one time
        // accidentally inside the class under test, then Mockito verify will fail the test.
        // usersRepository.save(user);

        if (!isUserCreated)
            throw new UserServiceException("could not create user");

        return user;
    }
}
