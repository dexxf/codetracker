package com.io.kira.application.auth.command;

public record AuthRegisterOAuthCommand(String email,String username, String role, long githubId, String accessToken) {
    
}