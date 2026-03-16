package com.io.codetracker.application.auth.error;

import com.io.codetracker.domain.auth.result.AuthCreationResult;

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


    public static AuthRegistrationError from(AuthCreationResult error) {
        return switch (error) {
            case USERNAME_TAKEN -> USERNAME_TAKEN;
            case EMPTY_EMAIL -> EMPTY_EMAIL;
            case INVALID_EMAIL_FORMAT -> INVALID_EMAIL_FORMAT;
            case INVALID_RAW_PASSWORD_LENGTH -> INVALID_RAW_PASSWORD_LENGTH;
            case INVALID_HASHED_FORMAT -> INVALID_HASHED_FORMAT;
            case HASHED_PASSWORD_EMPTY -> HASHED_PASSWORD_EMPTY;
            case INVALID_ROLE -> INVALID_ROLE;
            case INVALID_HASHED_PASSWORD_LENGTH -> INVALID_HASHED_PASSWORD_LENGTH;
        };
    }
}
