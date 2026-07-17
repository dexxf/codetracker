package com.io.codetracker.application.auth.error;

public enum AuthRegistrationError {
    USERNAME_TAKEN,
    EMPTY_EMAIL,
    EMAIL_TAKEN,
    INVALID_EMAIL_FORMAT,
    INVALID_RAW_PASSWORD_LENGTH,
    INVALID_HASHED_PASSWORD_LENGTH,
    INVALID_HASHED_FORMAT,
    HASHED_PASSWORD_EMPTY,
    INVALID_ROLE;
}
