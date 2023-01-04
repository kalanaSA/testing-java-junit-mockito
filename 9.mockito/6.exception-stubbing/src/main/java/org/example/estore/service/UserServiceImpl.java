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

        //boolean isUserCreated = usersRepository.save(user);
        boolean isUserCreated;
        try {
            //if 'save' method configured to throws RuntimeException(according to the test method's exception stubbing) when testing, then we need to catch it, and we need to throw
            //UserServiceException instead.
            isUserCreated = usersRepository.save(user);
        } catch (RuntimeException e) {
            throw new UserServiceException(e.getMessage());
        }

        if (!isUserCreated)
            throw new UserServiceException("could not create user");

        return user;
    }
}
