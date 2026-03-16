package com.io.codetracker.application.auth.port.in;

import com.io.codetracker.application.auth.command.AuthRegisterOAuthCommand;
import com.io.codetracker.application.auth.error.AuthRegistrationError;
import com.io.codetracker.application.auth.result.AuthData;
import com.io.codetracker.common.result.Result;

public interface AuthOAuthRegistrationUseCase {
    Result<AuthData, AuthRegistrationError> registerWithOAuth(AuthRegisterOAuthCommand command);
}