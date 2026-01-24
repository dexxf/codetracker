package com.io.codetracker.domain.auth.result;

public enum PasswordResult {
    INVALID_RAW_PASSWORD_LENGTH,
    INVALID_HASHED_PASSWORD_LENGTH,
    INVALID_HASHED_FORMAT,
    HASHED_PASSWORD_EMPTY,

}
