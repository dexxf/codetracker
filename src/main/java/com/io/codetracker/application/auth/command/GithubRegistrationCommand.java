package com.io.codetracker.application.auth.command;

public record GithubRegistrationCommand(
        String authId,
        Long githubId,
        String accessToken
) {
}
