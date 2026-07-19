package com.io.codetracker.application.auth.port.in;

import com.io.codetracker.application.auth.error.RegisterRefreshTokenError;
import com.io.codetracker.application.auth.result.RegisterRefreshTokenResult;
import com.io.codetracker.common.result.Result;

import java.util.UUID;

public interface AddRefreshTokenUseCase {
    Result<RegisterRefreshTokenResult, RegisterRefreshTokenError> add(UUID authId, String deviceId, String ipAddress, String userAgent);
}
