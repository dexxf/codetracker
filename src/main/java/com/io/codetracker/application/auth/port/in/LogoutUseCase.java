package com.io.codetracker.application.auth.port.in;

import com.io.codetracker.application.auth.command.LogoutCommand;
import com.io.codetracker.application.auth.result.LogoutResult;

public interface LogoutUseCase {
    LogoutResult execute(LogoutCommand command);
}
