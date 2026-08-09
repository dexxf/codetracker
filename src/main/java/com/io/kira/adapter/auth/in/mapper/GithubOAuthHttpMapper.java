package com.io.kira.adapter.auth.in.mapper;

import com.io.kira.application.auth.error.GithubOAuthSignInError;
import com.io.kira.application.auth.error.OAuthGithubCallbackError;
import org.springframework.http.HttpStatus;

public final class GithubOAuthHttpMapper {
    private GithubOAuthHttpMapper() {
    }

    public static HttpStatus toStatus(GithubOAuthSignInError error) {
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

    public static String toMessage(GithubOAuthSignInError error) {
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

    public static HttpStatus toStatus(OAuthGithubCallbackError error) {
        return switch (error) {
            case MISSING_CODE -> HttpStatus.BAD_REQUEST;
            case CODE_EXCHANGE_FAILED,
                 USER_INFO_FETCH_FAILED -> HttpStatus.BAD_GATEWAY;
        };
    }

    public static String toMessage(OAuthGithubCallbackError error) {
        return switch (error) {
            case MISSING_CODE -> "Missing OAuth code.";
            case CODE_EXCHANGE_FAILED -> "Failed to exchange code with GitHub.";
            case USER_INFO_FETCH_FAILED -> "Failed to fetch GitHub user info.";
        };
    }
}