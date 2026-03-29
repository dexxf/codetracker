package com.io.codetracker.adapter.auth.in.mapper;

import org.springframework.http.HttpStatus;
import com.io.codetracker.application.auth.error.GithubOAuthLoginError;

public final class GithubOAuthHttpMapper {
    private GithubOAuthHttpMapper() {
    }

    public static HttpStatus toStatus(GithubOAuthLoginError error) {
        return switch (error) {
            case USERNAME_TAKEN,
                 EMAIL_TAKEN,
                 ALREADY_LINKED -> HttpStatus.CONFLICT;
            case INVALID_DEVICE_ID,
                 REFRESH_TOKEN_CREATION_FAILED,
                 REFRESH_TOKEN_SAVE_FAILED -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(GithubOAuthLoginError error) {
        return switch (error) {
            case USERNAME_TAKEN -> "Username is already in use.";
            case EMPTY_EMAIL -> "Email must not be empty.";
            case EMAIL_TAKEN -> "Email is already in use.";
            case INVALID_EMAIL_FORMAT -> "Email format is invalid.";
            case INVALID_ROLE -> "Provided role is invalid.";
            case GITHUB_ID_NOT_FOUND -> "GitHub ID not found.";
            case ACCESS_TOKEN_MISSING -> "Access token is missing.";
            case ALREADY_LINKED -> "GitHub account is already linked.";
            case INVALID_DEVICE_ID -> "Device ID is invalid.";
            case REFRESH_TOKEN_CREATION_FAILED -> "Failed to create refresh token.";
            case REFRESH_TOKEN_SAVE_FAILED -> "Failed to save refresh token due to server error.";
            case INVALID_REFRESH_TOKEN_ID -> "Invalid Refresh token ID";
        };
    }
}
