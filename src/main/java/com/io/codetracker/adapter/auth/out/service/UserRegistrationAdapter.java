package com.io.codetracker.adapter.auth.out.service;

import com.io.codetracker.application.auth.port.out.UserRegistrationPort;
import com.io.codetracker.application.user.port.in.UserShallowRegistrationUseCase;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * used to expose the UserRegistration service to auth module.
 * auth module (AuthRegistration service) uses UserRegistrationPort interface to use the UserRegistration
 **/

@Component
public class UserRegistrationAdapter implements UserRegistrationPort {

    private final UserShallowRegistrationUseCase userShallowRegistrationUseCase;

    public UserRegistrationAdapter (UserShallowRegistrationUseCase userShallowRegistrationUseCase) {
        this.userShallowRegistrationUseCase = userShallowRegistrationUseCase;
    }

    @Override
    public UUID createShallowUser() {
        return userShallowRegistrationUseCase.createShallowUser();
    }
}

