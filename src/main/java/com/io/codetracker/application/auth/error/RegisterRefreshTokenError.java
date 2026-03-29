package com.io.codetracker.application.auth.error;

import com.io.codetracker.domain.auth.result.AuthRefreshTokenCreationResult;

public enum RegisterRefreshTokenError {
    AUTH_NOT_FOUND,
    INVALID_USER_ID,
    INVALID_DEVICE_ID,
    INVALID_TOKEN_HASH,
    INVALID_BECAUSE_EXPIRED_OR_REVOKED,
    CURRENT_TOKEN_IS_VALID,
    SAVE_FAILED,
    INVALID_REFRESH_TOKEN_ID;

    public static RegisterRefreshTokenError from(AuthRefreshTokenCreationResult error) {
        return switch (error) {
            case INVALID_USER_ID -> RegisterRefreshTokenError.INVALID_USER_ID;
            case INVALID_DEVICE_ID -> RegisterRefreshTokenError.INVALID_DEVICE_ID;
            case INVALID_TOKEN_HASH -> RegisterRefreshTokenError.INVALID_TOKEN_HASH;
            case INVALID_BECAUSE_EXPIRED_OR_REVOKED -> RegisterRefreshTokenError.INVALID_BECAUSE_EXPIRED_OR_REVOKED;
            case INVALID_REFRESH_TOKEN_ID -> RegisterRefreshTokenError.INVALID_REFRESH_TOKEN_ID;
        };
    }
}