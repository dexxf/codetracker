package com.io.codetracker.application.auth.result;

import java.time.Instant;

public record RefreshTokenRotationResult(
        String tokenId,
        String authId,
        String plainRefreshToken,
        Instant expiresAt
) {}
