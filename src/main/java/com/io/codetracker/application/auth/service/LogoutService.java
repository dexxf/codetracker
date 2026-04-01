package com.io.codetracker.application.auth.service;

import com.io.codetracker.application.auth.command.LogoutCommand;
import com.io.codetracker.application.auth.port.in.LogoutUseCase;
import com.io.codetracker.application.auth.port.out.AuthRefreshTokenAppRepository;
import com.io.codetracker.application.auth.result.LogoutResult;
import com.io.codetracker.domain.auth.entity.AuthRefreshToken;
import com.io.codetracker.domain.auth.service.PasswordHasher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LogoutService implements LogoutUseCase {

    private final AuthRefreshTokenAppRepository authRefreshTokenAppRepository;
    private final PasswordHasher hasher;

    @Override
    @Transactional
    public LogoutResult execute(LogoutCommand command) {
        if(command.deviceId() == null || command.deviceId().isBlank()) {
            return LogoutResult.DEVICE_ID_NOT_FOUND;
        }

        if(command.token() == null || command.token().isBlank()) {
            return LogoutResult.TOKEN_NOT_FOUND;
        }

        String[] tokenArr = command.token().split("\\.", 2);

        if (tokenArr.length != 2 || tokenArr[0].isBlank() || tokenArr[1].isBlank()) {
            return LogoutResult.INVALID_TOKEN;
        }

        UUID id;
        try {
            id = UUID.fromString(tokenArr[1]);
        } catch (IllegalArgumentException e) {
            return LogoutResult.INVALID_TOKEN_ID;
        }

        Optional<AuthRefreshToken> authRefreshTokenOptional = authRefreshTokenAppRepository.findByRefreshTokenId(id);
        if(authRefreshTokenOptional.isEmpty()) return LogoutResult.TOKEN_NOT_FOUND;

        AuthRefreshToken authRefreshToken = authRefreshTokenOptional.get();

        boolean deviceIdMatches = command.deviceId().equals(authRefreshToken.getDeviceId());
        if(!deviceIdMatches) return LogoutResult.DEVICE_ID_NOT_MATCH;

        boolean refreshTokenMatches= hasher.matches(tokenArr[0], authRefreshToken.getTokenHash());
        if(!refreshTokenMatches) return LogoutResult.REFRESH_TOKEN_NOT_MATCH;

        if(authRefreshToken.isRevoked()) return LogoutResult.TOKEN_REVOKED;

        boolean isRevokeSuccess = authRefreshTokenAppRepository.revokeToken(id, command.deviceId());

        if(!isRevokeSuccess) {
            return LogoutResult.REVOKE_FAILED;
        }
        return LogoutResult.SUCCESS;
    }
}
