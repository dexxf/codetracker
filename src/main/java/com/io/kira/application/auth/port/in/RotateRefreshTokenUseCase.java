package com.io.kira.application.auth.port.in;

import com.io.kira.application.auth.command.RotateRefreshTokenCommand;
import com.io.kira.application.auth.error.RefreshTokenRotationError;
import com.io.kira.application.auth.result.RefreshTokenRotationResult;
import com.io.kira.common.result.Result;

public interface RotateRefreshTokenUseCase {
    Result<RefreshTokenRotationResult, RefreshTokenRotationError> execute(RotateRefreshTokenCommand command);
}
