package com.io.kira.application.auth.result;

public record GithubExchangeCodeResult(
        String accessToken,
        String tokenType,
        String scope
) {
}