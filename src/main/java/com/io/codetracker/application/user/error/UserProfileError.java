package com.io.codetracker.application.user.error;

import com.io.codetracker.domain.user.result.UserProfileUpdateResult;

import java.util.List;

public enum UserProfileError {
    USER_NOT_FOUND,
    INVALID_FIRST_NAME,
    INVALID_LAST_NAME,
    INVALID_GENDER,
    INVALID_PHONE_NUMBER,
    INVALID_BIO,
    INVALID_BIRTHDAY;

    public static List<UserProfileError> from(List<UserProfileUpdateResult> results) {
        return results.stream()
                .map(result -> switch (result) {
                    case INVALID_FIRST_NAME -> INVALID_FIRST_NAME;
                    case INVALID_LAST_NAME -> INVALID_LAST_NAME;
                    case INVALID_GENDER -> INVALID_GENDER;
                    case INVALID_PHONE_NUMBER -> INVALID_PHONE_NUMBER;
                    case INVALID_BIO -> INVALID_BIO;
                    case INVALID_BIRTHDAY -> INVALID_BIRTHDAY;
                })
                .toList();
    }
}