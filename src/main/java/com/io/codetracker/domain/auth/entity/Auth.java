package com.io.codetracker.domain.auth.entity;

import com.io.codetracker.domain.auth.valueobject.Email;
import com.io.codetracker.domain.auth.valueobject.HashedPassword;
import com.io.codetracker.domain.auth.valueobject.Roles;
import com.io.codetracker.domain.auth.valueobject.Status;

import java.time.LocalDateTime;

public final class Auth {

    private final String authId;
    private final String userId;
    private Email email;
    private final String username;
    private HashedPassword password;
    private final LocalDateTime createdAt;
    private Status status;
    private Roles role;

    public Auth(String authId, String userId, Email email, String username, HashedPassword password, Roles role, Status status, LocalDateTime createdAt) {
        this.authId = authId;
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getAuthId() {
        return authId;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public HashedPassword getPassword() {
        return password;
    }

    public void setPassword(HashedPassword password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }
}