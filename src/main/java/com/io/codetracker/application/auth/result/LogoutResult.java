package com.io.codetracker.application.auth.result;

public enum LogoutResult {
    INVALID_TOKEN_ID,
    TOKEN_NOT_FOUND,
    DEVICE_ID_NOT_FOUND,
    SUCCESS,
    REFRESH_TOKEN_NOT_MATCH,
    DEVICE_ID_NOT_MATCH,
    TOKEN_REVOKED,
    REVOKE_FAILED,
    INVALID_TOKEN
}
