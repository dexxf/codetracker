package com.io.codetracker.application.auth.result;

public record GithubOAuthSignInData(
        String authId,
        boolean alreadyRegistered,
        String plainRefreshToken
) {
}