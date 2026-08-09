package com.io.kira.application.auth.result;

public record OAuthGithubCallbackResult(
        GithubExchangeCodeResult tokenResult,
        GithubFetchUserInfoResult userInfoResult
) {
}