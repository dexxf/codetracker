package com.io.codetracker.application.auth.error;

public enum GithubOAuthLoginError {
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

    public static GithubOAuthLoginError from(AuthRegistrationError error) {
        return GithubOAuthLoginError.valueOf(error.name());
    }

    public static GithubOAuthLoginError from(GithubAccountRegistrationError error) {
        return GithubOAuthLoginError.valueOf(error.name());
    }

    public static GithubOAuthLoginError from(RegisterRefreshTokenError error) {
        return switch (error) {
            case AUTH_NOT_FOUND -> GithubOAuthLoginError.GITHUB_ID_NOT_FOUND;
            case INVALID_DEVICE_ID -> GithubOAuthLoginError.INVALID_DEVICE_ID;
            case SAVE_FAILED -> GithubOAuthLoginError.REFRESH_TOKEN_SAVE_FAILED;
            case CURRENT_TOKEN_IS_VALID,
                 INVALID_USER_ID,
                 INVALID_TOKEN_HASH,
                 INVALID_BECAUSE_EXPIRED_OR_REVOKED -> GithubOAuthLoginError.REFRESH_TOKEN_CREATION_FAILED;
            case INVALID_REFRESH_TOKEN_ID -> GithubOAuthLoginError.INVALID_REFRESH_TOKEN_ID;
        };
    }
}