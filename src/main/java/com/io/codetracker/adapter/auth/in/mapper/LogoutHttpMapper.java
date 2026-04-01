package com.io.codetracker.adapter.auth.in.mapper;

import com.io.codetracker.application.auth.result.LogoutResult;
import org.springframework.http.HttpStatus;

public final class LogoutHttpMapper {

    private LogoutHttpMapper() {
    }

    public static HttpStatus toStatus(LogoutResult result) {
        return switch (result) {
            case SUCCESS,
                 TOKEN_NOT_FOUND,
                 TOKEN_REVOKED,
                 REFRESH_TOKEN_NOT_MATCH -> HttpStatus.OK;
            case DEVICE_ID_NOT_FOUND,
                 DEVICE_ID_NOT_MATCH,
                 INVALID_TOKEN,
                 INVALID_TOKEN_ID -> HttpStatus.BAD_REQUEST;
            case REVOKE_FAILED -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    public static String toMessage(LogoutResult result) {
        return switch (result) {
            case SUCCESS -> "Logged out successfully.";
            case TOKEN_NOT_FOUND -> "Refresh token was not found. Cookies were cleared.";
            case TOKEN_REVOKED -> "Refresh token was already revoked. Cookies were cleared.";
            case REFRESH_TOKEN_NOT_MATCH -> "Refresh token is invalid. Cookies were cleared.";
            case DEVICE_ID_NOT_FOUND -> "Device ID is required.";
            case DEVICE_ID_NOT_MATCH -> "Device ID does not match the refresh token.";
            case INVALID_TOKEN -> "Refresh token format is invalid.";
            case INVALID_TOKEN_ID -> "Refresh token ID is invalid.";
            case REVOKE_FAILED -> "Failed to revoke refresh token.";
        };
    }
}