package com.io.kira.application.auth.error;

public enum OAuthGithubCallbackError {
    MISSING_CODE,
    CODE_EXCHANGE_FAILED,
    USER_INFO_FETCH_FAILED
}