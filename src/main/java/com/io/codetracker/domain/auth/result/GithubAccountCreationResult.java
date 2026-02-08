package com.io.codetracker.domain.auth.result;

public enum GithubAccountCreationResult {
    GITHUB_ID_NOT_FOUND("GitHub ID was not found."),
    ACCESS_TOKEN_MISSING("Access token is missing."),
    ALREADY_LINKED("This GitHub account is already linked to another user.");

    private final String message;

    GithubAccountCreationResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
