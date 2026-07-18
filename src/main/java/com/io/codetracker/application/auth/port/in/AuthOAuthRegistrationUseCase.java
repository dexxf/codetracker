package com.io.codetracker.application.auth.port.in;

import com.io.codetracker.application.auth.command.AuthRegisterOAuthCommand;
import com.io.codetracker.application.auth.error.AuthRegistrationError;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.auth.aggregate.AuthAccountAggregate;

public interface AuthOAuthRegistrationUseCase {
    Result<AuthAccountAggregate, AuthRegistrationError> registerWithOAuth(AuthRegisterOAuthCommand command);
}