package com.io.codetracker.application.auth.service;

import java.util.Optional;
import com.io.codetracker.application.auth.error.RegisterRefreshTokenError;
import com.io.codetracker.application.auth.port.in.AddRefreshTokenUseCase;
import com.io.codetracker.application.auth.result.GithubAccountAttributes;
import com.io.codetracker.application.auth.result.RegisterRefreshTokenResult;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.io.codetracker.application.auth.command.AuthRegisterOAuthCommand;
import com.io.codetracker.application.auth.command.GithubOAuthLoginCommand;
import com.io.codetracker.application.auth.command.GithubRegistrationCommand;
import com.io.codetracker.application.auth.error.AuthRegistrationError;
import com.io.codetracker.application.auth.error.GithubAccountRegistrationError;
import com.io.codetracker.application.auth.error.GithubOAuthLoginError;
import com.io.codetracker.application.auth.port.in.AuthOAuthRegistrationUseCase;
import com.io.codetracker.application.auth.port.in.GithubAccountRegistrationUseCase;
import com.io.codetracker.application.auth.port.in.GithubOAuthLoginUseCase;
import com.io.codetracker.application.auth.port.out.GithubAppRepository;
import com.io.codetracker.application.auth.result.AuthData;
import com.io.codetracker.application.auth.result.GithubOAuthLoginData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.auth.entity.GithubAccount;

@Service
@AllArgsConstructor
public class GithubOAuthLoginService implements GithubOAuthLoginUseCase {
    private final GithubAppRepository githubAppRepository;
    private final AuthOAuthRegistrationUseCase authOAuthRegistrationUseCase;
    private final GithubAccountRegistrationUseCase githubAccountRegistrationUseCase;
    private final AddRefreshTokenUseCase addRefreshTokenUseCase;

    @Override
    public Result<GithubOAuthLoginData, GithubOAuthLoginError> loginOrRegister(GithubOAuthLoginCommand command) {
        Optional<GithubAccount> existingAccount = githubAppRepository.findByGithubId(command.githubId());

        if (existingAccount.isPresent()) {
            GithubAccount existing = existingAccount.get();
            if (command.accessToken() != null && !command.accessToken().isBlank()) {
                GithubAccount updatedAccount = new GithubAccount(
                        existing.getGithubAccountId(),
                        existing.getAuthId(),
                        existing.getGithubId(),
                        // TODO: create a method that updates the access token
                        //  directly instead of creating a new instance
                        command.accessToken());
                githubAppRepository.save(updatedAccount);
            }

            Result<RegisterRefreshTokenResult, RegisterRefreshTokenError> refreshTokenResult =
                    addRefreshTokenUseCase.add(
                            existing.getAuthId(),
                            command.deviceId(),
                            command.ipAddress(),
                            command.userAgent()
                    );

            if (!refreshTokenResult.success()) {
                return Result.fail(GithubOAuthLoginError.from(refreshTokenResult.error()));
            }

            String plainRefreshToken = refreshTokenResult.data().rawToken();

            return Result.ok(new GithubOAuthLoginData(
                    existing.getAuthId(),
                    true,
                    plainRefreshToken
            ));
        }

        Result<AuthData, AuthRegistrationError> authRegistrationResult =
                authOAuthRegistrationUseCase.registerWithOAuth(
                        new AuthRegisterOAuthCommand(
                                command.email(),
                                command.username(),
                                "USER"
                        )
                );

        if (!authRegistrationResult.success()) {
            return Result.fail(GithubOAuthLoginError.from(authRegistrationResult.error()));
        }

        String authId = authRegistrationResult.data().authId();

        Result<GithubAccountAttributes, GithubAccountRegistrationError> githubRegistrationResult =
                githubAccountRegistrationUseCase.registerGithubAccount(
                        new GithubRegistrationCommand(authId, command.githubId(), command.accessToken())
                );

        if (!githubRegistrationResult.success()) {
            return Result.fail(GithubOAuthLoginError.from(githubRegistrationResult.error()));
        }

        Result<RegisterRefreshTokenResult, RegisterRefreshTokenError> refreshTokenResult =
                addRefreshTokenUseCase.add(
                        authId,
                        command.deviceId(),
                        command.ipAddress(),
                        command.userAgent()
                );

        if (!refreshTokenResult.success()) {
            return Result.fail(GithubOAuthLoginError.from(refreshTokenResult.error()));
        }

        return Result.ok(new GithubOAuthLoginData(
                authId,
                false,
                refreshTokenResult.data().rawToken()
        ));
    }
}