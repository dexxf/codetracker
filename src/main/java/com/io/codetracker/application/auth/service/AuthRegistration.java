package com.io.codetracker.application.auth.service;

import com.io.codetracker.application.auth.command.AuthRegistrationCommand;
import com.io.codetracker.application.auth.response.AuthRegistrationResponseDTO;
import com.io.codetracker.application.auth.port.out.AuthAppRepository;
import com.io.codetracker.application.auth.port.out.UserRegistrationPort;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.auth.entity.Auth;
import com.io.codetracker.domain.auth.result.AuthCreationResult;
import com.io.codetracker.domain.auth.service.AuthCreationService;
import org.springframework.stereotype.Service;

@Service
public final class AuthRegistration {

    private final AuthCreationService authCreationService;
    private final AuthAppRepository authAppRepository;
    private final UserRegistrationPort userRegistration;

    public AuthRegistration(AuthCreationService authCreationService, AuthAppRepository authAppRepository, UserRegistrationPort userRegistration) {
        this.authCreationService = authCreationService;
        this.authAppRepository = authAppRepository;
        this.userRegistration = userRegistration;
    }

    public AuthRegistrationResponseDTO execute(AuthRegistrationCommand command) {
        if(authAppRepository.emailExists(command.email())) {
            return AuthRegistrationResponseDTO.fail("Email taken.");
        }

        String userId = userRegistration.createShallowUser();
        Result<Auth, AuthCreationResult> authCreationResult =
        authCreationService.createAuth(userId, command.email(), command.username(), command.rawPassword(), command.role().toUpperCase());

        if(!authCreationResult.success()) {
            return AuthRegistrationResponseDTO.fail(authCreationResult.error().getMessage());
        }

        Auth auth = authCreationResult.data();


        authAppRepository.save(authCreationResult.data());
        return AuthRegistrationResponseDTO.success(auth);
    }

}
