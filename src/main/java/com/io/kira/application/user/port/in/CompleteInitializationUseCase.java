package com.io.kira.application.user.port.in;


import java.util.UUID;
import com.io.kira.application.user.command.UserRegistrationCommand;
import com.io.kira.application.user.error.UserRegistrationError;
import com.io.kira.application.user.result.UserData;
import com.io.kira.common.result.Result;

public interface CompleteInitializationUseCase {
    Result<UserData, UserRegistrationError> completeInitialization(UUID userId, UserRegistrationCommand command);
}

