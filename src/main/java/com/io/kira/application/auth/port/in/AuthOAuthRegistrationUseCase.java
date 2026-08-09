package com.io.kira.application.auth.port.in;

import com.io.kira.application.auth.command.AuthRegisterOAuthCommand;
import com.io.kira.application.auth.error.AuthRegistrationError;
import com.io.kira.common.result.Result;
import com.io.kira.domain.auth.aggregate.AuthAccountAggregate;

public interface AuthOAuthRegistrationUseCase {
    Result<AuthAccountAggregate, AuthRegistrationError> registerWithOAuth(AuthRegisterOAuthCommand command);
}