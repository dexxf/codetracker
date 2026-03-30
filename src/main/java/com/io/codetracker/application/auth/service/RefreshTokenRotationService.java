package com.io.codetracker.application.auth.service;

import com.io.codetracker.application.auth.command.RotateRefreshTokenCommand;
import com.io.codetracker.application.auth.error.RefreshTokenRotationError;
import com.io.codetracker.application.auth.port.in.RotateRefreshTokenUseCase;
import com.io.codetracker.application.auth.port.out.AuthRefreshTokenAppRepository;
import com.io.codetracker.application.auth.result.RefreshTokenRotationResult;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.auth.entity.AuthRefreshToken;
import com.io.codetracker.domain.auth.service.PasswordHasher;
import com.io.codetracker.domain.auth.service.RefreshTokenLifetimePolicy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenRotationService implements RotateRefreshTokenUseCase {

    private final AuthRefreshTokenAppRepository authRefreshTokenAppRepository;
    private final PasswordHasher hashService;
    private final RefreshTokenLifetimePolicy refreshTokenLifetimePolicy;

    public RefreshTokenRotationService(
            AuthRefreshTokenAppRepository authRefreshTokenAppRepository,
            PasswordHasher hashService,
            RefreshTokenLifetimePolicy refreshTokenLifetimePolicy
    ) {
        this.authRefreshTokenAppRepository = authRefreshTokenAppRepository;
        this.hashService = hashService;
        this.refreshTokenLifetimePolicy = refreshTokenLifetimePolicy;
    }

    @Override
    public Result<RefreshTokenRotationResult, RefreshTokenRotationError> execute(
            RotateRefreshTokenCommand command) {

        if (command.deviceId() == null || command.deviceId().isBlank()) {
            return Result.fail(RefreshTokenRotationError.INVALID_DEVICE_ID);
        }

        if (command.plainRefreshToken() == null || command.plainRefreshToken().isBlank()) {
            return Result.fail(RefreshTokenRotationError.INVALID_TOKEN);
        }

        String[] refreshTokenArr = command.plainRefreshToken().split("\\.", 2);
        if (refreshTokenArr.length != 2 || refreshTokenArr[0].isBlank() || refreshTokenArr[1].isBlank()) {
            return Result.fail(RefreshTokenRotationError.INVALID_TOKEN);
        }

        String rawSecret = refreshTokenArr[0];
        UUID refreshTokenId;
        try {
            refreshTokenId = UUID.fromString(refreshTokenArr[1]);
        } catch (IllegalArgumentException e) {
            return Result.fail(RefreshTokenRotationError.INVALID_TOKEN);
        }

        Optional<AuthRefreshToken> existingTokenOpt = authRefreshTokenAppRepository.findByRefreshTokenId(refreshTokenId);

        if (existingTokenOpt.isEmpty()) {
            return Result.fail(RefreshTokenRotationError.TOKEN_NOT_FOUND);
        }

        AuthRefreshToken existingToken = existingTokenOpt.get();
        String authId = existingToken.getAuthId();

        try {
            if (existingToken.isRevoked()) {
                return Result.fail(RefreshTokenRotationError.TOKEN_REVOKED);
            }

            if (existingToken.getExpiresAt().isBefore(LocalDateTime.now())) {
                return Result.fail(RefreshTokenRotationError.TOKEN_EXPIRED);
            }

            if (!command.deviceId().equals(existingToken.getDeviceId())) {
                return Result.fail(RefreshTokenRotationError.INVALID_DEVICE_ID);
            }

            if (!hashService.matches(rawSecret, existingToken.getTokenHash())) {
                return Result.fail(RefreshTokenRotationError.INVALID_TOKEN);
            }

            String newRawSecret = UUID.randomUUID().toString();
            String newHashedToken = hashService.encode(newRawSecret);
            LocalDateTime newExpiry = refreshTokenLifetimePolicy.issueExpirationFromNow();

            if (!authRefreshTokenAppRepository.updateToken(existingToken.getId(), newHashedToken, newExpiry, command.ipAddress(), command.userAgent())) {
                return Result.fail(RefreshTokenRotationError.SAVE_FAILED);
            }

            return Result.ok(new RefreshTokenRotationResult(
                    existingToken.getId().toString(),
                    authId,
                    buildPlainRefreshToken(newRawSecret, existingToken.getId()),
                    newExpiry
            ));

        } catch (Exception e) {
            return Result.fail(RefreshTokenRotationError.SAVE_FAILED);
        }
    }

    private String buildPlainRefreshToken(String rawSecret, UUID tokenId) {
        return rawSecret + "." + tokenId;
    }
}
