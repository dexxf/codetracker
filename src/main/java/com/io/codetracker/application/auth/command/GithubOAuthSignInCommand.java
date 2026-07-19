package com.io.codetracker.application.auth.command;

public record GithubOAuthSignInCommand(
        String email,
        String username,
        Long githubId,
        String accessToken,
        String deviceId,
        String ipAddress,
        String userAgent
) {
}