package com.io.kira.domain.auth.entity;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public final class AuthRefreshToken {

    private UUID id;
    private UUID authId;
    private String tokenHash;
    private Instant expiresAt;
    private boolean revoked;
    private Instant revokedAt;
    private Instant lastUsedAt;
    private String deviceId;
    private String ipAddress;
    private String userAgent;

    public AuthRefreshToken(UUID id, UUID authId, String tokenHash, Instant expiresAt, boolean revoked,
                            Instant revokedAt, Instant lastUsedAt, String deviceId,
                            String ipAddress, String userAgent) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.authId = Objects.requireNonNull(authId, "authId must not be null");
        this.tokenHash = Objects.requireNonNull(tokenHash, "tokenHash must not be null");
        this.expiresAt = Objects.requireNonNull(expiresAt, "expiresAt must not be null");
        this.revoked = revoked;
        this.revokedAt = revokedAt;
        this.lastUsedAt = lastUsedAt;
        this.deviceId = deviceId;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }

    public static AuthRefreshToken createNew(UUID authId, String tokenHash, Instant expiresAt,
                                             String deviceId, String ipAddress, String userAgent) {
        return new AuthRefreshToken(
                UUID.randomUUID(),
                authId,
                tokenHash,
                expiresAt,
                false,
                null,
                null,
                deviceId,
                ipAddress,
                userAgent
        );
    }

    public static AuthRefreshToken reconstitute(UUID id, UUID authId, String tokenHash, Instant expiresAt,
                                                boolean revoked, Instant revokedAt, Instant lastUsedAt,
                                                String deviceId, String ipAddress, String userAgent) {
        return new AuthRefreshToken(
                id,
                authId,
                tokenHash,
                expiresAt,
                revoked,
                revokedAt,
                lastUsedAt,
                deviceId,
                ipAddress,
                userAgent
        );
    }

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public boolean isValid() {
        return !revoked && !isExpired();
    }

    public void markAsRevoked() {
        this.revoked = true;
        this.revokedAt = Instant.now();
    }

    public void updateLastUsedAt() {
        this.lastUsedAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getAuthId() {
        return authId;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public Instant getRevokedAt() {
        return revokedAt;
    }

    public Instant getLastUsedAt() {
        return lastUsedAt;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setAuthId(UUID authId) {
        this.authId = authId;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public void setRevokedAt(Instant revokedAt) {
        this.revokedAt = revokedAt;
    }

    public void setLastUsedAt(Instant lastUsedAt) {
        this.lastUsedAt = lastUsedAt;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
}
