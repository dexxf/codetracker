package com.io.codetracker.application.user.port.in;


import java.util.UUID;
import com.io.codetracker.application.user.command.UserRegistrationCommand;
import com.io.codetracker.application.user.error.UserRegistrationError;
import com.io.codetracker.application.user.result.UserData;
import com.io.codetracker.common.result.Result;

public interface CompleteInitializationUseCase {
    Result<UserData, UserRegistrationError> completeInitialization(UUID userId, UserRegistrationCommand command);
}

