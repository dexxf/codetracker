package com.io.codetracker.application.auth.error;

public enum GithubOAuthLoginError {
    USERNAME_TAKEN,
    EMPTY_EMAIL,
    EMAIL_TAKEN,
    INVALID_EMAIL_FORMAT,
    INVALID_ROLE,
    GITHUB_ID_NOT_FOUND,
    ACCESS_TOKEN_MISSING,
    ALREADY_LINKED;

    public static GithubOAuthLoginError from(AuthRegistrationError error) {
        return GithubOAuthLoginError.valueOf(error.name());
    }

    public static GithubOAuthLoginError from(GithubAccountRegistrationError error) {
        return GithubOAuthLoginError.valueOf(error.name());
    }
}