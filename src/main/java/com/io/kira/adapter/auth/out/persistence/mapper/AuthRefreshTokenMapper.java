package com.io.kira.adapter.auth.out.persistence.mapper;

import com.io.kira.domain.auth.entity.AuthRefreshToken;
import com.io.kira.infrastructure.auth.persistence.entity.AuthRefreshTokenEntity;

public class AuthRefreshTokenMapper {
    public static AuthRefreshToken toDomain(AuthRefreshTokenEntity entity) {
        if (entity == null) return null;
        return new AuthRefreshToken(
                entity.getId(),
                entity.getAuthEntity().getId(),
                entity.getTokenHash(),
                entity.getExpiresAt(),
                entity.isRevoked(),
                entity.getRevokedAt(),
                entity.getLastUsedAt(),
                entity.getDeviceId(),
                entity.getIpAddress(),
                entity.getUserAgent()
        );
    }

    public static AuthRefreshTokenEntity toEntity(AuthRefreshToken domain) {
        if (domain == null) return null;
        return AuthRefreshTokenEntity.builder()
                .id(domain.getId())
                .tokenHash(domain.getTokenHash())
                .expiresAt(domain.getExpiresAt())
                .revoked(domain.isRevoked())
                .revokedAt(domain.getRevokedAt())
                .lastUsedAt(domain.getLastUsedAt())
                .deviceId(domain.getDeviceId())
                .ipAddress(domain.getIpAddress())
                .userAgent(domain.getUserAgent())
                .build();
    }
}