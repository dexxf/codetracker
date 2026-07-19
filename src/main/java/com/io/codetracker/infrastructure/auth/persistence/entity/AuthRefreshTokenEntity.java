package com.io.codetracker.infrastructure.auth.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity(name = "refresh_token")
@Table(name = "refresh_token", indexes = {
        @Index(name = "idx_token_hash", columnList = "token_hash"),
        @Index(name = "idx_expires_at", columnList = "expires_at"),
        @Index(name = "idx_revoked", columnList = "revoked"),
        @Index(name = "idx_device_id", columnList = "device_id")
},
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_auth_device",
                        columnNames = {"auth_id", "device_id"}
                )
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRefreshTokenEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_id")
    private AuthEntity authEntity;

    @Column(nullable = false, length = 255)
    private String tokenHash;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private boolean revoked;

    private Instant revokedAt;
    private Instant lastUsedAt;

    @Column(name = "device_id", nullable = false, length = 36)
    private String deviceId;

    @Column(length = 50)
    private String ipAddress;

    @Column(length = 500)
    private String userAgent;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}

