package org.example.estore.service;

import org.example.estore.data.UsersRepository;
import org.example.estore.model.User;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    UsersRepository usersRepository;
    EmailVerificationService emailVerificationService; /* create an instance of EmailVerificationService. add as dependency to 'UserServiceImpl'. because we've
    injected EmailVerificationService as dependency into userServiceImpl class, we can now mock emailVerificationService and inject its mock version instead.
    this will allow us to test the 'createUser' method in isolation from the real implementation of EmailVerificationService.  */

    public UserServiceImpl(UsersRepository usersRepository, EmailVerificationService emailVerificationService) {
        this.usersRepository = usersRepository;
        this.emailVerificationService = emailVerificationService; //initialize it in constructor.
    }

    @Override public User createUser(String firstName, String lastName, String email, String password, String repeatPassword) {

        if (firstName == null || firstName.trim().length() == 0) {
            throw new IllegalArgumentException("User's first name is empty");
        }

        if (lastName == null || lastName.trim().length() == 0) {
            throw new IllegalArgumentException("User's last name is empty");
        }

        User user = new User(firstName, lastName, email, UUID.randomUUID().toString());

        boolean isUserCreated;
        try {
            isUserCreated = usersRepository.save(user);
        } catch (RuntimeException e) {
            throw new UserServiceException(e.getMessage());
        }

        if (!isUserCreated)
            throw new UserServiceException("could not create user");

        //let's assume, after a user was created and persisted in our storage, we need to send that user an email with request to confirm their email address.
        //using 'scheduleEmailConfirmation' void method
        try {
            emailVerificationService.scheduleEmailConfirmation(user);
        } catch (RuntimeException e) {
            throw new UserServiceException(e.getMessage());
        }

        return user;
    }
}
