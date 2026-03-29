package com.io.codetracker.domain.auth.entity;

import java.time.LocalDateTime;
import java.util.UUID;

public class AuthRefreshToken {

    private UUID id;
    private String authId;
    private String tokenHash;
    private LocalDateTime expiresAt;
    private boolean revoked;
    private LocalDateTime revokedAt;
    private LocalDateTime lastUsedAt;
    private String deviceId;
    private String ipAddress;
    private String userAgent;

    public AuthRefreshToken(UUID id, String authId, String tokenHash, LocalDateTime expiresAt, boolean revoked,
                            LocalDateTime revokedAt, LocalDateTime lastUsedAt, String deviceId,
                            String ipAddress, String userAgent) {
        this.id = id;
        this.authId = authId;
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
        this.revoked = revoked;
        this.revokedAt = revokedAt;
        this.lastUsedAt = lastUsedAt;
        this.deviceId = deviceId;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isValid() {
        return !revoked && !isExpired();
    }

    public void markAsRevoked() {
        this.revoked = true;
        this.revokedAt = LocalDateTime.now();
    }

    public void updateLastUsedAt() {
        this.lastUsedAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public String getAuthId() {
        return authId;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public LocalDateTime getRevokedAt() {
        return revokedAt;
    }

    public LocalDateTime getLastUsedAt() {
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

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public void setRevokedAt(LocalDateTime revokedAt) {
        this.revokedAt = revokedAt;
    }

    public void setLastUsedAt(LocalDateTime lastUsedAt) {
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