package com.io.codetracker.application.auth.command;

public record RotateRefreshTokenCommand(
        String plainRefreshToken,
        String deviceId,
        String ipAddress,
        String userAgent
) {
}