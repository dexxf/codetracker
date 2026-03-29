package com.io.codetracker.application.auth.result;

import java.time.LocalDateTime;

public record RefreshTokenRotationResult(
        String tokenId,
        String authId,
        String plainRefreshToken,
        LocalDateTime expiresAt
) {}