package org.example.estore.service;

import org.example.estore.model.User;

public class EmailVerificationServiceImpl implements EmailVerificationService {
    @Override public void scheduleEmailConfirmation(User user) {
        //put user details into email queue.
        System.out.println("scheduleEmailConfirmation method called!");
    }
}
