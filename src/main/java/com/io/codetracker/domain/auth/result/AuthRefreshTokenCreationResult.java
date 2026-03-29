package com.io.codetracker.domain.auth.result;

public enum AuthRefreshTokenCreationResult {
    INVALID_USER_ID,
    INVALID_TOKEN_HASH,
    INVALID_DEVICE_ID,
    INVALID_BECAUSE_EXPIRED_OR_REVOKED,
    INVALID_REFRESH_TOKEN_ID;
}
