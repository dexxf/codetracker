package com.io.codetracker.domain.user.result;

public enum UserCreationResult {

    USER_NULL("User cannot be null."),
    NAME_REQUIRED("Name is required."),
    PHONE_NUMBER_EMPTY("Phone number cannot be empty."),
    INVALID_PHONE_NUMBER_FORMAT("Phone number format is invalid."),
    BIRTHDAY_IN_FUTURE("Birthday cannot be in the future."),
    TOO_OLD("User is too old."),
    TOO_YOUNG("User is too young."),
    INVALID_GENDER("Invalid gender."),
    SUCCESS("User created successfully.");

    private final String message;

    UserCreationResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
