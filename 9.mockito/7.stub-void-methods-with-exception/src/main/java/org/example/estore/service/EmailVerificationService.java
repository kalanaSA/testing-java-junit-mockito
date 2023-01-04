package org.example.estore.service;

import org.example.estore.model.User;

public interface EmailVerificationService {

    /**
     * used to put user details into a queue, so that email can be sent to this user.
     *
     * @param user
     */
    void scheduleEmailConfirmation(User user);

}
