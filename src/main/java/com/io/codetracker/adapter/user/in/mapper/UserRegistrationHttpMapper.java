package com.io.codetracker.adapter.user.in.mapper;

import org.springframework.http.HttpStatus;

import com.io.codetracker.application.user.error.UserRegistrationError;

public final class UserRegistrationHttpMapper {

    private UserRegistrationHttpMapper() {}

    public static HttpStatus toStatus(UserRegistrationError error) {
        return switch (error) {
            case USER_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case USER_ALREADY_INITIALIZED -> HttpStatus.CONFLICT;
            case PROFILE_UPLOAD_FAILED, PROFILE_DELETE_FAILED -> HttpStatus.INTERNAL_SERVER_ERROR;
            case INVALID_USER_DATA -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(UserRegistrationError error) {
        return switch (error) {
            case USER_NOT_FOUND -> "User not found.";
            case USER_ALREADY_INITIALIZED -> "User is already fully initialized.";
            case PROFILE_UPLOAD_FAILED -> "Failed to upload profile image.";
            case PROFILE_DELETE_FAILED -> "Failed to delete uploaded profile image.";
            case INVALID_USER_DATA -> "Invalid user registration data.";
        };
    }
}