package com.io.codetracker.adapter.auth.in.dto.response;

import java.time.LocalDateTime;

public record RotateRefreshTokenResponse(
        LocalDateTime expiresAt,
        String message
) {

    public static RotateRefreshTokenResponse ok(LocalDateTime expiresAt) {
        return new RotateRefreshTokenResponse(expiresAt, "Token refreshed successfully");
    }

    public static RotateRefreshTokenResponse fail(String errorMessage) {
        return new RotateRefreshTokenResponse(null, errorMessage);
    }
}