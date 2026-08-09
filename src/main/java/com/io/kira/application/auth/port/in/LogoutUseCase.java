package com.io.kira.application.auth.port.in;

import com.io.kira.application.auth.command.LogoutCommand;
import com.io.kira.application.auth.result.LogoutResult;

public interface LogoutUseCase {
    LogoutResult execute(LogoutCommand command);
}
