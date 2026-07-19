package com.io.codetracker.application.auth.result;

import java.util.UUID;

public record GithubOAuthSignInData(
        UUID authId,
        boolean alreadyRegistered,
        String plainRefreshToken
) {
}
