package com.io.codetracker.domain.auth.result;

public enum AuthCreationResult {
    USERNAME_TAKEN("Username is already taken."),
    EMPTY_EMAIL("Email cannot be empty."),
    INVALID_EMAIL_FORMAT("Email format is invalid."),
    INVALID_RAW_PASSWORD_LENGTH("Raw password length is invalid."),
    INVALID_HASHED_PASSWORD_LENGTH("Hashed password length is invalid."),
    INVALID_HASHED_FORMAT("Hashed password format is invalid."),
    HASHED_PASSWORD_EMPTY("Hashed password cannot be empty."),
    INVALID_ROLE("Please select a valid role");

    private final String message;

    AuthCreationResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}