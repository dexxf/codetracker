package com.io.codetracker.adapter.auth.in.mapper;

import org.springframework.http.HttpStatus;
import com.io.codetracker.application.auth.error.RefreshTokenRotationError;

public final class RotateRefreshTokenHttpMapper {

    private RotateRefreshTokenHttpMapper() {
    }

    public static HttpStatus toStatus(RefreshTokenRotationError error) {
        return switch (error) {
            case SAVE_FAILED -> HttpStatus.INTERNAL_SERVER_ERROR;
            case TOKEN_REVOKED,
                 INVALID_DEVICE_ID,
                 INVALID_TOKEN,
                 TOKEN_NOT_FOUND,
                 TOKEN_EXPIRED -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(RefreshTokenRotationError error) {
        return switch (error) {
            case TOKEN_NOT_FOUND -> "Refresh token not found for the provided device ID.";
            case SAVE_FAILED -> "Failed to save the refresh token due to a server error.";
            case INVALID_DEVICE_ID -> "The device ID provided is invalid.";
            case INVALID_TOKEN -> "Refresh token is invalid.";
            case TOKEN_REVOKED -> "Refresh token is revoked.";
            case TOKEN_EXPIRED -> "Refresh token is expired already.";
        };
    }
}