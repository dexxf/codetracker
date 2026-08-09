package com.io.kira.application.auth.command;

public record RotateRefreshTokenCommand(
        String plainRefreshToken,
        String deviceId,
        String ipAddress,
        String userAgent
) {
}