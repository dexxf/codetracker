package com.io.codetracker.application.auth.command;

public record AuthRegisterOAuthCommand(String email,String username, String role) {
    
}