package com.io.codetracker.application.auth.command;

public record GithubOAuthLoginCommand(
        String email,
        String username,
        Long githubId,
        String accessToken,
        String deviceId,
        String ipAddress,
        String userAgent
) {
}