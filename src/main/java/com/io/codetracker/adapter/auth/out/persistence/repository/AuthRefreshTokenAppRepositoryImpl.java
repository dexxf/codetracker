package com.io.codetracker.adapter.auth.out.persistence.repository;

import com.io.codetracker.adapter.auth.out.persistence.mapper.AuthRefreshTokenMapper;
import com.io.codetracker.application.auth.port.out.AuthRefreshTokenAppRepository;
import com.io.codetracker.domain.auth.entity.AuthRefreshToken;
import com.io.codetracker.infrastructure.auth.persistence.entity.AuthEntity;
import com.io.codetracker.infrastructure.auth.persistence.entity.AuthRefreshTokenEntity;
import com.io.codetracker.infrastructure.auth.persistence.repository.JpaAuthRefreshTokenRepository;
import com.io.codetracker.infrastructure.auth.persistence.repository.JpaAuthRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class AuthRefreshTokenAppRepositoryImpl implements AuthRefreshTokenAppRepository {
    private final JpaAuthRefreshTokenRepository jpaRTRepository;
    private final JpaAuthRepository jpaAuthRepository;

    @Override
    public Optional<AuthRefreshToken> findTokenByAuthIdAndDeviceId(String authId, String deviceId) {
        return jpaRTRepository.findByAuthEntity_AuthIdAndDeviceId(
                authId,
                deviceId
        ).map(AuthRefreshTokenMapper::toDomain);
    }

    @Override
    public boolean createToken(AuthRefreshToken authRefreshToken) {
        try {
            Optional<AuthEntity> entityOpt = jpaAuthRepository.findById(authRefreshToken.getAuthId());
            if (entityOpt.isEmpty()) {
                return false;
            }

            AuthEntity entity = entityOpt.get();
            AuthRefreshTokenEntity refreshTokenEntity = AuthRefreshTokenMapper.toEntity(authRefreshToken);
            refreshTokenEntity.setAuthEntity(entity);
            jpaRTRepository.save(refreshTokenEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateToken(UUID id, String hashedToken, LocalDateTime newExpiry, String ipAddress, String userAgent) {
        try {
            Optional<AuthRefreshTokenEntity> authRefreshTokenEntityOpt = jpaRTRepository.findById(id);
            if(authRefreshTokenEntityOpt.isEmpty()) return false;
            AuthRefreshTokenEntity authRefreshTokenEntity = authRefreshTokenEntityOpt.get();

            authRefreshTokenEntity.setExpiresAt(newExpiry);
            authRefreshTokenEntity.setTokenHash(hashedToken);
            authRefreshTokenEntity.setRevoked(false);
            authRefreshTokenEntity.setRevokedAt(null);
            authRefreshTokenEntity.setIpAddress(ipAddress);
            authRefreshTokenEntity.setUserAgent(userAgent);
            authRefreshTokenEntity.setUpdatedAt(LocalDateTime.now());

            jpaRTRepository.save(authRefreshTokenEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Optional<AuthRefreshToken> findByRefreshTokenId(UUID refreshTokenId) {
        return jpaRTRepository.findById(refreshTokenId).map(AuthRefreshTokenMapper::toDomain);
    }
}
