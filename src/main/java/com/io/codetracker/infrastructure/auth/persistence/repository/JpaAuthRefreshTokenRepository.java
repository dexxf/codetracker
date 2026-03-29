package com.io.codetracker.infrastructure.auth.persistence.repository;

import com.io.codetracker.infrastructure.auth.persistence.entity.AuthRefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface JpaAuthRefreshTokenRepository extends JpaRepository<AuthRefreshTokenEntity, UUID> {
    Optional<AuthRefreshTokenEntity> findByAuthEntity_AuthIdAndDeviceIdAndRevokedFalseAndExpiresAtAfter(String authEntityAuthId, String deviceId, LocalDateTime expiresAtAfter);
}
