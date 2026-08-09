package com.io.kira.application.auth.port.out;

import com.io.kira.domain.auth.entity.AuthRefreshToken;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface AuthRefreshTokenAppRepository {
    Optional<AuthRefreshToken> findTokenByAuthIdAndDeviceId(UUID authId, String deviceId);
    boolean createToken(AuthRefreshToken authRefreshToken);
    boolean updateToken(UUID id, String hashedToken, Instant newExpiry, String ipAddress, String userAgent);
    Optional<AuthRefreshToken> findByRefreshTokenId(UUID refreshTokenID);
    boolean revokeToken(UUID id, String deviceId);
}

