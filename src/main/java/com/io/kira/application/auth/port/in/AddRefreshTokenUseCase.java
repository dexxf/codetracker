package com.io.kira.application.auth.port.in;

import com.io.kira.application.auth.error.RegisterRefreshTokenError;
import com.io.kira.application.auth.result.RegisterRefreshTokenResult;
import com.io.kira.common.result.Result;

import java.util.UUID;

public interface AddRefreshTokenUseCase {
    Result<RegisterRefreshTokenResult, RegisterRefreshTokenError> add(UUID authId, String deviceId, String ipAddress, String userAgent);
}
