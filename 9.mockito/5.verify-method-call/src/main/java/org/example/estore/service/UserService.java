package org.example.estore.service;

import org.example.estore.model.User;

public interface UserService {

    User createUser(String firstName, String lastName, String email, String password, String repeatPassword);

}
