package com.io.codetracker.adapter.auth.in.dto.response;

import java.time.Instant;

public record RotateRefreshTokenResponse(
        Instant expiresAt,
        String message
) {

    public static RotateRefreshTokenResponse ok(Instant expiresAt) {
        return new RotateRefreshTokenResponse(expiresAt, "Token refreshed successfully");
    }

    public static RotateRefreshTokenResponse fail(String errorMessage) {
        return new RotateRefreshTokenResponse(null, errorMessage);
    }
}
