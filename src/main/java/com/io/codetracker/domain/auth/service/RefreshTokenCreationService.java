package com.io.codetracker.domain.auth.service;

import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.auth.entity.AuthRefreshToken;
import com.io.codetracker.domain.auth.result.AuthRefreshTokenCreationResult;

import java.time.LocalDateTime;
import java.util.UUID;

public class RefreshTokenCreationService {
    private final RefreshTokenLifetimePolicy refreshTokenLifetimePolicy;

    public RefreshTokenCreationService(RefreshTokenLifetimePolicy refreshTokenLifetimePolicy) {
        this.refreshTokenLifetimePolicy = refreshTokenLifetimePolicy;
    }

    public Result<AuthRefreshToken, AuthRefreshTokenCreationResult> createAndValidate(UUID refreshTokenId, String authId, String hashedToken, String deviceId, String ipAddress, String userAgent) {

        if(refreshTokenId == null) {
            return Result.fail(AuthRefreshTokenCreationResult.INVALID_REFRESH_TOKEN_ID);
        }

        if (authId == null || authId.isBlank()) {
            return Result.fail(AuthRefreshTokenCreationResult.INVALID_USER_ID);
        }

        if (hashedToken == null || hashedToken.isBlank()) {
            return Result.fail(AuthRefreshTokenCreationResult.INVALID_TOKEN_HASH);
        }

        if (deviceId == null || deviceId.isBlank()) {
            return Result.fail(AuthRefreshTokenCreationResult.INVALID_DEVICE_ID);
        }

        LocalDateTime expiresAt = refreshTokenLifetimePolicy.issueExpirationFromNow();

        AuthRefreshToken token = new AuthRefreshToken(
                refreshTokenId,
                authId,
                hashedToken,
                expiresAt,
                false,
                null,
                LocalDateTime.now(),
                deviceId,
                ipAddress,
                userAgent
        );

        if (!token.isValid()) {
            return Result.fail(AuthRefreshTokenCreationResult.INVALID_BECAUSE_EXPIRED_OR_REVOKED);
        }

        return Result.ok(token);
    }
}