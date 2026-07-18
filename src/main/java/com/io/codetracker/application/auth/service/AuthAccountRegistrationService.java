package com.io.codetracker.application.auth.service;

import com.io.codetracker.application.auth.command.AuthRegisterOAuthCommand;
import com.io.codetracker.application.auth.error.AuthRegistrationError;
import com.io.codetracker.application.auth.port.in.AuthOAuthRegistrationUseCase;
import com.io.codetracker.application.auth.port.out.AuthAppRepository;
import com.io.codetracker.application.auth.port.out.UserRegistrationPort;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.auth.aggregate.AuthAccountAggregate;
import com.io.codetracker.domain.auth.factory.AuthAccountAggregateFactory;
import com.io.codetracker.domain.auth.result.EmailResult;
import com.io.codetracker.domain.auth.valueobject.Email;
import com.io.codetracker.domain.auth.valueobject.Roles;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public final class AuthAccountRegistrationService implements AuthOAuthRegistrationUseCase{

    private final AuthAppRepository authAppRepository;
    private final UserRegistrationPort userRegistration;
    private final AuthAccountAggregateFactory authAccountAggregateFactory;

    public AuthAccountRegistrationService(AuthAppRepository authAppRepository, UserRegistrationPort userRegistration, AuthAccountAggregateFactory authAccountAggregateFactory) {
        this.authAppRepository = authAppRepository;
        this.userRegistration = userRegistration;
        this.authAccountAggregateFactory = authAccountAggregateFactory;
    }

    public Result<AuthAccountAggregate, AuthRegistrationError> registerWithOAuth(AuthRegisterOAuthCommand command) {
        if(authAppRepository.emailExists(command.email())) {
            return Result.fail(AuthRegistrationError.EMAIL_TAKEN);
        }

        if (authAppRepository.existsByUsername(command.username())) {
            return Result.fail(AuthRegistrationError.USERNAME_TAKEN);
        }

        Result<Email, EmailResult> emailResult = Email.of(command.email());
        if (!emailResult.success()) {
            return switch (emailResult.error()) {
                case EMAIL_EMPTY -> Result.fail(AuthRegistrationError.EMPTY_EMAIL);
                case INVALID_EMAIL_FORMAT -> Result.fail(AuthRegistrationError.INVALID_EMAIL_FORMAT);
            };
        }

        Roles selectedRole;
        try {
            selectedRole = Roles.valueOf(command.role().toUpperCase(Locale.ROOT));
        } catch (RuntimeException e) {
            return Result.fail(AuthRegistrationError.INVALID_ROLE);
        }

        String userId = userRegistration.createShallowUser();

        AuthAccountAggregate aggregate = authAccountAggregateFactory.create(userId,emailResult.data(), command.username(), selectedRole,command.githubId(), command.accessToken());

        authAppRepository.save(aggregate);
        return Result.ok(aggregate);
    }

}
