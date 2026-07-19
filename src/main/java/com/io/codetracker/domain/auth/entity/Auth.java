package com.io.codetracker.domain.auth.entity;

import com.io.codetracker.domain.auth.valueobject.Email;
import com.io.codetracker.domain.auth.valueobject.HashedPassword;
import com.io.codetracker.domain.auth.valueobject.Roles;
import com.io.codetracker.domain.auth.valueobject.Status;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class Auth {

    private final UUID authId;
    private final UUID userId;
    private Email email;
    private final String username;
    private HashedPassword password;
    private final Instant createdAt;
    private Status status;
    private Roles role;

    private Auth(UUID authId, UUID userId, Email email, String username, HashedPassword password, Roles role, Status status, Instant createdAt) {
        this.authId = Objects.requireNonNull(authId, "authId must not be null");
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.email = Objects.requireNonNull(email, "email must not be null");
        this.username = Objects.requireNonNull(username, "username must not be null");
        this.password = password;
        this.role = Objects.requireNonNull(role, "role must not be null");
        this.status = Objects.requireNonNull(status, "status must not be null");
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
    }

    public static Auth createOAuth(UUID authId, UUID userId, Email email, String username, Roles role) {
        return new Auth(
                Objects.requireNonNull(authId, "authId must not be null"),
                userId,
                email,
                username,
                null,
                role,
                Status.INACTIVE,
                Instant.now()
        );
    }

    public static Auth reconstitute(
            UUID authId,
            UUID userId,
            Email email,
            String username,
            HashedPassword password,
            Roles role,
            Status status,
            Instant createdAt
    ) {
        return new Auth(authId, userId, email, username, password, role, status, createdAt);
    }

    public UUID getAuthId() {
        return authId;
    }

    public Roles getRole() {
        return role;
    }

    public void changeRole(Roles role) {
        this.role = Objects.requireNonNull(role, "role must not be null");
    }

    public Status getStatus() {
        return status;
    }

    public void changeStatus(Status status) {
        this.status = Objects.requireNonNull(status, "status must not be null");
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public HashedPassword getPassword() {
        return password;
    }

    public void changePassword(HashedPassword password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public Email getEmail() {
        return email;
    }

    public void changeEmail(Email email) {
        this.email = Objects.requireNonNull(email, "email must not be null");
    }

    public UUID getUserId() {
        return userId;
    }
}

