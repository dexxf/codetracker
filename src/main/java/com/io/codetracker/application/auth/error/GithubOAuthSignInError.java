package com.io.codetracker.application.auth.error;

public enum GithubOAuthSignInError {
    USERNAME_TAKEN,
    EMPTY_EMAIL,
    EMAIL_TAKEN,
    INVALID_EMAIL_FORMAT,
    INVALID_ROLE,
    GITHUB_ID_NOT_FOUND,
    ACCESS_TOKEN_MISSING,
    ALREADY_LINKED,
    INVALID_DEVICE_ID,
    REFRESH_TOKEN_CREATION_FAILED,
    REFRESH_TOKEN_SAVE_FAILED,
    INVALID_REFRESH_TOKEN_ID;

    public static GithubOAuthSignInError from(AuthRegistrationError error) {
        return switch (error) {
            case USERNAME_TAKEN -> USERNAME_TAKEN;
            case EMPTY_EMAIL -> EMPTY_EMAIL;
            case EMAIL_TAKEN -> EMAIL_TAKEN;
            case INVALID_EMAIL_FORMAT -> INVALID_EMAIL_FORMAT;
            case INVALID_ROLE -> INVALID_ROLE;
        };
    }

    public static GithubOAuthSignInError from(RegisterRefreshTokenError error) {
        return switch (error) {
            case AUTH_NOT_FOUND -> GithubOAuthSignInError.GITHUB_ID_NOT_FOUND;
            case INVALID_DEVICE_ID -> GithubOAuthSignInError.INVALID_DEVICE_ID;
            case SAVE_FAILED -> GithubOAuthSignInError.REFRESH_TOKEN_SAVE_FAILED;
            case CURRENT_TOKEN_IS_VALID,
                 INVALID_USER_ID,
                 INVALID_TOKEN_HASH,
                 INVALID_BECAUSE_EXPIRED_OR_REVOKED -> GithubOAuthSignInError.REFRESH_TOKEN_CREATION_FAILED;
            case INVALID_REFRESH_TOKEN_ID -> GithubOAuthSignInError.INVALID_REFRESH_TOKEN_ID;
        };
    }
}