package com.io.codetracker.application.auth.result;


import java.time.LocalDateTime;

public record RegisterRefreshTokenResult(
        String id,
        String authId,
        LocalDateTime expiresAt,
        String rawToken
) {
}