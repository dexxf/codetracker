package com.io.codetracker.application.auth.service;

import java.util.Optional;

import com.io.codetracker.application.auth.command.AuthRegisterOAuthCommand;
import com.io.codetracker.application.auth.command.GithubOAuthLoginCommand;
import com.io.codetracker.application.auth.error.AuthRegistrationError;
import com.io.codetracker.application.auth.error.GithubOAuthLoginError;
import com.io.codetracker.application.auth.error.RegisterRefreshTokenError;
import com.io.codetracker.application.auth.port.in.AddRefreshTokenUseCase;
import com.io.codetracker.application.auth.port.in.AuthOAuthRegistrationUseCase;
import com.io.codetracker.application.auth.port.in.GithubOAuthLoginUseCase;
import com.io.codetracker.application.auth.port.out.AuthAppRepository;
import com.io.codetracker.application.auth.port.out.UserRegistrationPort;
import com.io.codetracker.application.auth.result.GithubOAuthLoginData;
import com.io.codetracker.application.auth.result.RegisterRefreshTokenResult;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.auth.aggregate.AuthAccountAggregate;
import com.io.codetracker.domain.auth.entity.GithubAccount;
import com.io.codetracker.domain.auth.valueobject.Roles;
import com.io.codetracker.domain.auth.valueobject.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GithubOAuthLoginService implements GithubOAuthLoginUseCase {


    private final AuthAppRepository authAppRepository;
    private final UserRegistrationPort userRegistration;
    private final AddRefreshTokenUseCase addRefreshTokenUseCase;
    private final AuthOAuthRegistrationUseCase authOAuthRegistrationUseCase;

    @Override
    public Result<GithubOAuthLoginData, GithubOAuthLoginError> loginOrRegister(GithubOAuthLoginCommand command) {
        Optional<AuthAccountAggregate> existingAccount = authAppRepository.findByGithubId(command.githubId());

        if (existingAccount.isPresent()) {
            AuthAccountAggregate aggregate = existingAccount.get();
            GithubAccount existing = aggregate.githubAccount();
            if (command.accessToken() != null && !command.accessToken().isBlank()) {
                existing.updateAccessToken(command.accessToken());
                authAppRepository.save(aggregate);
            }

            Result<RegisterRefreshTokenResult, RegisterRefreshTokenError> refreshTokenResult =
                    addRefreshTokenUseCase.add(
                            existing.getId(),
                            command.deviceId(),
                            command.ipAddress(),
                            command.userAgent()
                    );

            if (!refreshTokenResult.success()) {
                return Result.fail(GithubOAuthLoginError.from(refreshTokenResult.error()));
            }

            String plainRefreshToken = refreshTokenResult.data().rawToken();

            boolean alreadyInitialized = authAppRepository.findByAuthId(existing.getId())
                    .map(auth -> auth.getStatus() == Status.ACTIVE)
                    .orElse(false);

            return Result.ok(new GithubOAuthLoginData(
                    existing.getId(),
                    alreadyInitialized,
                    plainRefreshToken
            ));
        }

        if (authAppRepository.emailExists(command.email())) {
            return Result.fail(GithubOAuthLoginError.EMAIL_TAKEN);
        }

        if (authAppRepository.existsByUsername(command.username())) {
            return Result.fail(GithubOAuthLoginError.USERNAME_TAKEN);
        }

        Roles selectedRole = Roles.USER;

        Result<AuthAccountAggregate, AuthRegistrationError> authRegistrationResult = authOAuthRegistrationUseCase.registerWithOAuth(
                new AuthRegisterOAuthCommand(
                        command.email(),
                        command.username(),
                        selectedRole.name(),
                        command.githubId(),
                        command.accessToken()
                )
        );

        if (!authRegistrationResult.success()) {
            return Result.fail(GithubOAuthLoginError.from(authRegistrationResult.error()));
        }

        AuthAccountAggregate aggregate = authRegistrationResult.data();

        authAppRepository.save(aggregate);

        Result<RegisterRefreshTokenResult, RegisterRefreshTokenError> refreshTokenResult =
                addRefreshTokenUseCase.add(
                        aggregate.auth().getAuthId(),
                        command.deviceId(),
                        command.ipAddress(),
                        command.userAgent()
                );

        if (!refreshTokenResult.success()) {
            return Result.fail(GithubOAuthLoginError.from(refreshTokenResult.error()));
        }

        return Result.ok(new GithubOAuthLoginData(
                aggregate.auth().getAuthId(),
                false,
                refreshTokenResult.data().rawToken()
        ));
    }
}