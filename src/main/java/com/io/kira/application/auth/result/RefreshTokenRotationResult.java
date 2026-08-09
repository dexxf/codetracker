package com.io.kira.application.auth.result;

import java.time.Instant;
import java.util.UUID;

public record RefreshTokenRotationResult(
        String tokenId,
        UUID authId,
        String plainRefreshToken,
        Instant expiresAt
) {}
