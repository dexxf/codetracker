package com.io.kira.application.auth.service;

import com.io.kira.application.auth.error.RegisterRefreshTokenError;
import com.io.kira.application.auth.port.in.AddRefreshTokenUseCase;
import com.io.kira.application.auth.port.out.AuthAppRepository;
import com.io.kira.application.auth.port.out.AuthRefreshTokenAppRepository;
import com.io.kira.application.auth.result.RegisterRefreshTokenResult;
import com.io.kira.common.result.Result;
import com.io.kira.domain.auth.entity.AuthRefreshToken;
import com.io.kira.domain.auth.service.PasswordHasher;
import com.io.kira.application.auth.port.out.RefreshTokenLifetimePolicy;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegisterRefreshTokenService implements AddRefreshTokenUseCase {

    private final AuthRefreshTokenAppRepository rTokenRepository;
    private final AuthAppRepository authAppRepository;
    private final RefreshTokenLifetimePolicy refreshTokenLifetimePolicy;
    private final PasswordHasher hashService;

    public RegisterRefreshTokenService(AuthRefreshTokenAppRepository rTokenRepository, AuthAppRepository authAppRepository,
                                       RefreshTokenLifetimePolicy refreshTokenLifetimePolicy, PasswordHasher hashService) {
        this.rTokenRepository = rTokenRepository;
        this.authAppRepository = authAppRepository;
        this.refreshTokenLifetimePolicy = refreshTokenLifetimePolicy;
        this.hashService = hashService;
    }

    @Override
    public Result<RegisterRefreshTokenResult, RegisterRefreshTokenError> add(UUID authId, String deviceId, String ipAddress, String userAgent) {
        if (!authAppRepository.existsByAuthId(authId))
            return Result.fail(RegisterRefreshTokenError.AUTH_NOT_FOUND);

        if (deviceId == null || deviceId.isBlank())
            return Result.fail(RegisterRefreshTokenError.INVALID_DEVICE_ID);

        Instant expiresAt = refreshTokenLifetimePolicy.issueExpirationFromNow();
        String rawSecret = UUID.randomUUID().toString();
        String hashedSecret = hashService.encode(rawSecret);

        Optional<AuthRefreshToken> existingToken = rTokenRepository.findTokenByAuthIdAndDeviceId(authId, deviceId);

        if (existingToken.isPresent()) {
            AuthRefreshToken tokenToUpdate = existingToken.get();
            boolean isUpdateSuccess = rTokenRepository.updateToken(tokenToUpdate.getId(), hashedSecret, expiresAt, ipAddress, userAgent);

            if (!isUpdateSuccess) {
                return Result.fail(RegisterRefreshTokenError.SAVE_FAILED);
            }

            return Result.ok(new RegisterRefreshTokenResult(
                    tokenToUpdate.getId().toString(),
                    tokenToUpdate.getAuthId(),
                    expiresAt,
                    buildPlainRefreshToken(rawSecret, tokenToUpdate.getId())
            ));

        } else {

            AuthRefreshToken newToken =
                    AuthRefreshToken.createNew(
                            authId,
                            hashedSecret,
                            refreshTokenLifetimePolicy.issueExpirationFromNow(),
                            deviceId,
                            ipAddress,
                            userAgent
                    );

            if (!rTokenRepository.createToken(newToken))
                return Result.fail(RegisterRefreshTokenError.SAVE_FAILED);

            return Result.ok(new RegisterRefreshTokenResult(
                    newToken.getId().toString(),
                    newToken.getAuthId(),
                    newToken.getExpiresAt(),
                    buildPlainRefreshToken(rawSecret, newToken.getId())
            ));
        }
    }

    private String buildPlainRefreshToken(String rawSecret, UUID tokenId) {
        return rawSecret + "." + tokenId;
    }
}

