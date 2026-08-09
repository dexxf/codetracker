package com.io.kira.application.auth.command;

public record LogoutCommand(String deviceId, String token) {
}
