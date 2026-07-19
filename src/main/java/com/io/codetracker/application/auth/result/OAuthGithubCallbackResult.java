package com.io.codetracker.application.auth.result;

public record OAuthGithubCallbackResult(
        GithubExchangeCodeResult tokenResult,
        GithubFetchUserInfoResult userInfoResult
) {
}