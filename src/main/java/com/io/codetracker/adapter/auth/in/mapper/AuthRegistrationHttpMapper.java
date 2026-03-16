package com.io.codetracker.adapter.auth.in.mapper;

import com.io.codetracker.application.auth.error.AuthRegistrationError;
import org.springframework.http.HttpStatus;

public final class AuthRegistrationHttpMapper {

    private AuthRegistrationHttpMapper() {}

    public static HttpStatus toStatus(AuthRegistrationError error) {
        return switch (error) {
            case USERNAME_TAKEN, EMAIL_TAKEN -> HttpStatus.CONFLICT;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(AuthRegistrationError error) {
        return switch (error) {
            case USERNAME_TAKEN -> "Username is already in use.";
            case EMPTY_EMAIL -> "Email must not be empty.";
            case EMAIL_TAKEN -> "Email is already in use.";
            case INVALID_EMAIL_FORMAT -> "Email format is invalid.";
            case INVALID_RAW_PASSWORD_LENGTH -> "Password length is invalid.";
            case INVALID_ROLE -> "Provided role is invalid.";
            default -> "Failed to register user authentication.";
        };
    }
}