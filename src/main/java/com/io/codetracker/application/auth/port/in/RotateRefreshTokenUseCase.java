package com.io.codetracker.application.auth.port.in;

import com.io.codetracker.application.auth.command.RotateRefreshTokenCommand;
import com.io.codetracker.application.auth.error.RefreshTokenRotationError;
import com.io.codetracker.application.auth.result.RefreshTokenRotationResult;
import com.io.codetracker.common.result.Result;

public interface RotateRefreshTokenUseCase {
    Result<RefreshTokenRotationResult, RefreshTokenRotationError> execute(RotateRefreshTokenCommand command);
}
