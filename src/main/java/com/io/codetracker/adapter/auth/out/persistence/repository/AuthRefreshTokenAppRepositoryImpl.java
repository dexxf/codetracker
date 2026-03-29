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
    public Optional<AuthRefreshToken> findValidTokenByAuthIdAndDeviceId(String authId, String deviceId) {
        return jpaRTRepository.findByAuthEntity_AuthIdAndDeviceIdAndRevokedFalseAndExpiresAtAfter(
                authId,
                deviceId,
                LocalDateTime.now()
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
    public boolean updateToken(UUID id, String hashedToken, LocalDateTime newExpiry) {
        try {
            // TODO: will refactor to just use query instead of finding the id, updating and saving.
            Optional<AuthRefreshTokenEntity> authRefreshTokenEntityOpt = jpaRTRepository.findById(id);
            if(authRefreshTokenEntityOpt.isEmpty()) return false;
            AuthRefreshTokenEntity authRefreshTokenEntity = authRefreshTokenEntityOpt.get();

            authRefreshTokenEntity.setExpiresAt(newExpiry);
            authRefreshTokenEntity.setTokenHash(hashedToken);
            authRefreshTokenEntity.setUpdatedAt(LocalDateTime.now());

            jpaRTRepository.save(authRefreshTokenEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
