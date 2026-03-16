package com.io.codetracker.application.auth.service;

import com.io.codetracker.application.auth.command.AuthRegisterOAuthCommand;
import com.io.codetracker.application.auth.error.AuthRegistrationError;
import com.io.codetracker.application.auth.result.AuthData;
import com.io.codetracker.application.auth.port.in.AuthOAuthRegistrationUseCase;
import com.io.codetracker.application.auth.port.out.AuthAppRepository;
import com.io.codetracker.application.auth.port.out.UserRegistrationPort;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.auth.entity.Auth;
import com.io.codetracker.domain.auth.result.AuthCreationResult;
import com.io.codetracker.domain.auth.service.AuthCreationService;
import org.springframework.stereotype.Service;

@Service
public final class AuthRegistrationService implements AuthOAuthRegistrationUseCase{

    private final AuthCreationService authCreationService;
    private final AuthAppRepository authAppRepository;
    private final UserRegistrationPort userRegistration;

    public AuthRegistrationService(AuthCreationService authCreationService, AuthAppRepository authAppRepository, UserRegistrationPort userRegistration) {
        this.authCreationService = authCreationService;
        this.authAppRepository = authAppRepository;
        this.userRegistration = userRegistration;
    }

    public Result<AuthData, AuthRegistrationError> registerWithOAuth(AuthRegisterOAuthCommand command) {
        if(authAppRepository.emailExists(command.email())) {
            return Result.fail(AuthRegistrationError.EMAIL_TAKEN);
        }

        String userId = userRegistration.createShallowUser();
        Result<Auth, AuthCreationResult> authCreationResult =
        authCreationService.createAuthWithOAuth(userId, command.email(), command.username(), command.role().toUpperCase());

        if(!authCreationResult.success()) {
            return Result.fail(AuthRegistrationError.from(authCreationResult.error()));
        }

        Auth auth = authCreationResult.data();

        authAppRepository.save(authCreationResult.data());
        return Result.ok(AuthData.from(auth));
    }

}
