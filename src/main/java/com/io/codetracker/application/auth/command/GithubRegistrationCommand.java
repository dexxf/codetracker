package com.io.codetracker.application.auth.command;

import lombok.NonNull;

import jakarta.validation.constraints.NotBlank;

public record GithubRegistrationCommand(
        @NotBlank String authId,
        @NonNull Long githubId,
        @NotBlank String accessToken
) {
}
