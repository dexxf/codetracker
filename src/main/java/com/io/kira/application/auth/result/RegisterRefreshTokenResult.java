package com.io.kira.application.auth.result;


import java.time.Instant;
import java.util.UUID;

public record RegisterRefreshTokenResult(
        String id,
        UUID authId,
        Instant expiresAt,
        String rawToken
) {
}
