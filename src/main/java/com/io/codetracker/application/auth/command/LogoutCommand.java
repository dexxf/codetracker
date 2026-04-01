package com.io.codetracker.application.auth.command;

public record LogoutCommand(String deviceId, String token) {
}
