package com.io.codetracker.application.auth.service;

import java.util.Optional;

import com.io.codetracker.application.auth.command.AuthRegisterOAuthCommand;
import com.io.codetracker.application.auth.command.GithubOAuthSignInCommand;
import com.io.codetracker.application.auth.error.AuthRegistrationError;
import com.io.codetracker.application.auth.error.GithubOAuthSignInError;
import com.io.codetracker.application.auth.error.RegisterRefreshTokenError;
import com.io.codetracker.application.auth.port.in.AddRefreshTokenUseCase;
import com.io.codetracker.application.auth.port.in.AuthOAuthRegistrationUseCase;
import com.io.codetracker.application.auth.port.in.OAuthGithubSignInUseCase;
import com.io.codetracker.application.auth.port.out.AuthAppRepository;
import com.io.codetracker.application.auth.result.GithubOAuthSignInData;
import com.io.codetracker.application.auth.result.RegisterRefreshTokenResult;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.auth.aggregate.AuthAccountAggregate;
import com.io.codetracker.domain.auth.entity.Auth;
import com.io.codetracker.domain.auth.entity.GithubAccount;
import com.io.codetracker.domain.auth.valueobject.Roles;
import com.io.codetracker.domain.auth.valueobject.Status;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OAuthGithubSignInService implements OAuthGithubSignInUseCase {

    private final AuthAppRepository authAppRepository;
    private final AddRefreshTokenUseCase addRefreshTokenUseCase;
    private final AuthOAuthRegistrationUseCase authOAuthRegistrationUseCase;

    @Override
    public Result<GithubOAuthSignInData, GithubOAuthSignInError> loginOrRegister(GithubOAuthSignInCommand command) {
        Optional<AuthAccountAggregate> existingAccount = authAppRepository.findByGithubId(command.githubId());

        if (existingAccount.isPresent()) {
            AuthAccountAggregate aggregate = existingAccount.get();
            GithubAccount existing = aggregate.githubAccount();
            if (command.accessToken() != null && !command.accessToken().isBlank()) {
                existing.updateAccessToken(command.accessToken());
                authAppRepository.save(aggregate);
            }

            boolean alreadyInitialized = authAppRepository.findByAuthId(existing.getId())
                    .map(auth -> auth.getStatus() == Status.ACTIVE)
                    .orElse(false);

            return completeSignIn(existing.getId(), alreadyInitialized, command);
        }

        Optional<Auth> authWithSameEmail = authAppRepository.findByEmail(command.email());
        if (authWithSameEmail.isPresent()) {
            Auth auth = authWithSameEmail.get();
            AuthAccountAggregate recoveredAggregate = new AuthAccountAggregate(auth, new GithubAccount(auth.getAuthId(), command.githubId(), command.accessToken()));

            authAppRepository.save(recoveredAggregate);

            return completeSignIn(auth.getAuthId(), auth.getStatus() == Status.ACTIVE, command);
        }

        if (authAppRepository.emailExists(command.email())) {
            return Result.fail(GithubOAuthSignInError.EMAIL_TAKEN);
        }

        if (authAppRepository.existsByUsername(command.username())) {
            return Result.fail(GithubOAuthSignInError.USERNAME_TAKEN);
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
            return Result.fail(GithubOAuthSignInError.from(authRegistrationResult.error()));
        }

        AuthAccountAggregate aggregate = authRegistrationResult.data();

        return completeSignIn(aggregate.auth().getAuthId(), false, command);
    }

    private Result<GithubOAuthSignInData, GithubOAuthSignInError> completeSignIn(String authId, boolean alreadyInitialized, GithubOAuthSignInCommand command) {
        Result<RegisterRefreshTokenResult, RegisterRefreshTokenError> refreshTokenResult =
                addRefreshTokenUseCase.add(
                        authId,
                        command.deviceId(),
                        command.ipAddress(),
                        command.userAgent()
                );

        if (!refreshTokenResult.success()) {
            return Result.fail(GithubOAuthSignInError.from(refreshTokenResult.error()));
        }

        return Result.ok(new GithubOAuthSignInData(
                authId,
                alreadyInitialized,
                refreshTokenResult.data().rawToken()
        ));
    }
}
