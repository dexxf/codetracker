package com.io.codetracker.application.auth.result;


import java.time.Instant;
import java.util.UUID;

public record RegisterRefreshTokenResult(
        String id,
        UUID authId,
        Instant expiresAt,
        String rawToken
) {
}
