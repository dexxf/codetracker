package com.io.codetracker.application.auth.result;

public record GithubOAuthLoginData(
        String authId,
        boolean alreadyRegistered,
        String plainRefreshToken
) {
}