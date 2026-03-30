package com.io.codetracker.application.auth.service;

import com.io.codetracker.application.auth.error.RegisterRefreshTokenError;
import com.io.codetracker.application.auth.port.in.AddRefreshTokenUseCase;
import com.io.codetracker.application.auth.port.out.AuthAppRepository;
import com.io.codetracker.application.auth.port.out.AuthRefreshTokenAppRepository;
import com.io.codetracker.application.auth.result.RegisterRefreshTokenResult;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.auth.entity.AuthRefreshToken;
import com.io.codetracker.domain.auth.result.AuthRefreshTokenCreationResult;
import com.io.codetracker.domain.auth.service.PasswordHasher;
import com.io.codetracker.domain.auth.service.RefreshTokenCreationService;
import com.io.codetracker.domain.auth.service.RefreshTokenLifetimePolicy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegisterRefreshTokenService implements AddRefreshTokenUseCase {

    private final AuthRefreshTokenAppRepository rTokenRepository;
    private final RefreshTokenCreationService refreshTokenCreationService;
    private final AuthAppRepository authAppRepository;
    private final RefreshTokenLifetimePolicy refreshTokenLifetimePolicy;
    private final PasswordHasher hashService;

    public RegisterRefreshTokenService(AuthRefreshTokenAppRepository rTokenRepository, AuthAppRepository authAppRepository,
                                       RefreshTokenCreationService refreshTokenCreationService,
                                       RefreshTokenLifetimePolicy refreshTokenLifetimePolicy, PasswordHasher hashService) {
        this.rTokenRepository = rTokenRepository;
        this.authAppRepository = authAppRepository;
        this.refreshTokenCreationService = refreshTokenCreationService;
        this.refreshTokenLifetimePolicy = refreshTokenLifetimePolicy;
        this.hashService = hashService;
    }

    @Override
    public Result<RegisterRefreshTokenResult, RegisterRefreshTokenError> add(String authId, String deviceId, String ipAddress, String userAgent) {
        if (!authAppRepository.existsByAuthId(authId))
            return Result.fail(RegisterRefreshTokenError.AUTH_NOT_FOUND);

        if (deviceId == null || deviceId.isBlank())
            return Result.fail(RegisterRefreshTokenError.INVALID_DEVICE_ID);

        LocalDateTime expiresAt = refreshTokenLifetimePolicy.issueExpirationFromNow();
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
            UUID refreshTokenId = UUID.randomUUID();
            Result<AuthRefreshToken, AuthRefreshTokenCreationResult> rtCreationResult =
                    refreshTokenCreationService.createAndValidate(
                            refreshTokenId,
                            authId,
                            hashedSecret,
                            deviceId,
                            ipAddress,
                            userAgent
                    );

            if (!rtCreationResult.success())
                return Result.fail(RegisterRefreshTokenError.from(rtCreationResult.error()));

            AuthRefreshToken newToken = rtCreationResult.data();

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
