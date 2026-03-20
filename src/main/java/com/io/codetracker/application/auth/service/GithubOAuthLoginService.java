package com.io.codetracker.application.auth.service;

import java.util.Optional;

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
public class GithubOAuthLoginService implements GithubOAuthLoginUseCase {

    private final GithubAppRepository githubAppRepository;
    private final AuthOAuthRegistrationUseCase authOAuthRegistrationUseCase;
    private final GithubAccountRegistrationUseCase githubAccountRegistrationUseCase;

    public GithubOAuthLoginService(
            GithubAppRepository githubAppRepository,
            AuthOAuthRegistrationUseCase authOAuthRegistrationUseCase,
            GithubAccountRegistrationUseCase githubAccountRegistrationUseCase
    ) {
        this.githubAppRepository = githubAppRepository;
        this.authOAuthRegistrationUseCase = authOAuthRegistrationUseCase;
        this.githubAccountRegistrationUseCase = githubAccountRegistrationUseCase;
    }

    @Override
    public Result<GithubOAuthLoginData, GithubOAuthLoginError> loginOrRegister(GithubOAuthLoginCommand command) {
        Optional<GithubAccount> existingAccount = githubAppRepository.findByGithubId(command.githubId());

        if (existingAccount.isPresent()) {
            GithubAccount existing = existingAccount.get();
            if (command.accessToken() != null
                    && !command.accessToken().isBlank()
                    && !command.accessToken().equals(existing.getAccessToken())) {
                GithubAccount updatedAccount = new GithubAccount(
                        existing.getGithubAccountId(),
                        existing.getAuthId(),
                        existing.getGithubId(),
                        command.accessToken()
                );
                githubAppRepository.save(updatedAccount);
            }
            return Result.ok(new GithubOAuthLoginData(existing.getAuthId(), true));
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

        Result<?, GithubAccountRegistrationError> githubRegistrationResult =
                githubAccountRegistrationUseCase.registerGithubAccount(
                        new GithubRegistrationCommand(authId, command.githubId(), command.accessToken())
                );

        if (!githubRegistrationResult.success()) {
            return Result.fail(GithubOAuthLoginError.from(githubRegistrationResult.error()));
        }

        return Result.ok(new GithubOAuthLoginData(authId, false));
    }
}