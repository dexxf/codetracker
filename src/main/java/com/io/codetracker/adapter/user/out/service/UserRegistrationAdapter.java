package com.io.codetracker.adapter.user.out.service;

import com.io.codetracker.application.auth.port.out.UserRegistrationPort;
import com.io.codetracker.application.user.service.UserRegistration;
import org.springframework.stereotype.Component;

/**
 * used to expose the UserRegistration service to auth module.
 * auth module (AuthRegistration service) uses UserRegistrationPort interface to use the UserRegistration
 **/

@Component
public class UserRegistrationAdapter implements UserRegistrationPort {

    private final UserRegistration userRegistration;

    public UserRegistrationAdapter (UserRegistration userRegistration) {
        this.userRegistration = userRegistration;
    }

    @Override
    public String createShallowUser() {
        return userRegistration.createShallowUser();
    }
}
