package com.io.kira.application.auth.service;

import com.io.kira.application.auth.command.AuthRegisterOAuthCommand;
import com.io.kira.application.auth.error.AuthRegistrationError;
import com.io.kira.application.auth.port.in.AuthOAuthRegistrationUseCase;
import com.io.kira.application.auth.port.out.AuthAppRepository;
import com.io.kira.application.auth.port.out.UserRegistrationPort;
import com.io.kira.common.result.Result;
import com.io.kira.domain.auth.aggregate.AuthAccountAggregate;
import com.io.kira.domain.auth.factory.AuthAccountAggregateFactory;
import com.io.kira.domain.auth.result.EmailResult;
import com.io.kira.domain.auth.valueobject.Email;
import com.io.kira.domain.auth.valueobject.Roles;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;

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

        UUID userId = userRegistration.createShallowUser();

        AuthAccountAggregate aggregate = authAccountAggregateFactory.create(userId,emailResult.data(), command.username(), selectedRole,command.githubId(), command.accessToken());

        authAppRepository.save(aggregate);
        return Result.ok(aggregate);
    }

}

