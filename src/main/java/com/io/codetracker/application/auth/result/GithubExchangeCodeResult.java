package com.io.codetracker.application.auth.result;

public record GithubExchangeCodeResult(
        String accessToken,
        String tokenType,
        String scope
) {
}