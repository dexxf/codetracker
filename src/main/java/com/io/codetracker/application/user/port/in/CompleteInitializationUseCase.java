package com.io.codetracker.application.user.port.in;

import com.io.codetracker.application.user.command.UserRegistrationCommand;
import com.io.codetracker.application.user.error.UserRegistrationError;
import com.io.codetracker.application.user.result.UserData;
import com.io.codetracker.common.result.Result;

public interface CompleteInitializationUseCase {
    Result<UserData, UserRegistrationError> completeInitialization(String userId, UserRegistrationCommand command);
}
