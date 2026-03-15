package com.io.codetracker.adapter.user.in.mapper;

import org.springframework.http.HttpStatus;
import com.io.codetracker.application.user.error.UserProfileError;

import java.util.List;

public final class UserProfileHttpMapper {

    private UserProfileHttpMapper() {}

    public static HttpStatus toStatus(List<UserProfileError> error) {
        return error.contains(UserProfileError.USER_NOT_FOUND) ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
    }

    private static String toMessage(UserProfileError error) {
        return switch (error) {
            case USER_NOT_FOUND -> "User not found.";
            case INVALID_FIRST_NAME -> "First name is invalid.";
            case INVALID_LAST_NAME -> "Last name is invalid.";
            case INVALID_GENDER -> "Gender is invalid.";
            case INVALID_PHONE_NUMBER -> "Phone number is invalid.";
            case INVALID_BIO -> "Bio must be 350 characters or less.";
            case INVALID_BIRTHDAY -> "Birthday is invalid.";
            default -> "Invalid user data";
        };
    }

    public static List<String> toMessages(List<UserProfileError> errors) {
        return errors.stream().map(UserProfileHttpMapper::toMessage).toList();
    }
}