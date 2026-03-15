package com.io.codetracker.domain.user.result;

public enum UserCreationResult {
    USER_NULL,
    NAME_REQUIRED,
    PHONE_NUMBER_EMPTY,
    INVALID_PHONE_NUMBER_FORMAT,
    BIRTHDAY_IN_FUTURE,
    TOO_OLD,
    TOO_YOUNG,
    INVALID_GENDER,
    SUCCESS;
}