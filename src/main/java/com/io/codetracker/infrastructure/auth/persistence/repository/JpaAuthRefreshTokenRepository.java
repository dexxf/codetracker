package com.io.codetracker.infrastructure.auth.persistence.repository;

import com.io.codetracker.infrastructure.auth.persistence.entity.AuthRefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface JpaAuthRefreshTokenRepository extends JpaRepository<AuthRefreshTokenEntity, UUID> {
    Optional<AuthRefreshTokenEntity> findByAuthEntity_AuthIdAndDeviceId(String authId, String deviceId);
    @Modifying
    @Query("UPDATE refresh_token rt SET  rt.revoked = true, rt.revokedAt = :revokedAt WHERE rt.id = :id AND rt.deviceId = :deviceId")
    int revokeByIdAndDeviceId(@Param("id") UUID id, @Param("deviceId") String deviceId, @Param("revokedAt") LocalDateTime revokedAt);
}
