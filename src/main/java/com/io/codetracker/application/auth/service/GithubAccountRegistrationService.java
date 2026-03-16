package com.io.codetracker.application.auth.service;

import com.io.codetracker.application.auth.command.GithubRegistrationCommand;
import com.io.codetracker.application.auth.error.GithubAccountRegistrationError;

import org.springframework.stereotype.Service;

import com.io.codetracker.application.auth.port.out.GithubAppRepository;
import com.io.codetracker.application.auth.result.GithubAccountAttributes;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.auth.entity.GithubAccount;
import com.io.codetracker.domain.auth.result.GithubAccountCreationResult;
import com.io.codetracker.domain.auth.service.GithubAccountCreationService;

@Service
public class GithubAccountRegistrationService {

    private final GithubAccountCreationService ghCreationService;
    private final GithubAppRepository ghAppRepository;

    public GithubAccountRegistrationService(GithubAccountCreationService ghCreationService, GithubAppRepository ghAppRepository) {
        this.ghCreationService = ghCreationService;
        this.ghAppRepository = ghAppRepository;
    }

    public Result<GithubAccountAttributes, GithubAccountRegistrationError> registerGithubAccount(GithubRegistrationCommand command) {

        Result<GithubAccount, GithubAccountCreationResult> result =
            ghCreationService.create(command.authId(), command.githubId(), command.accessToken());

        if (!result.success()) {
            return Result.fail(GithubAccountRegistrationError.from(result.error()));
        }

        GithubAccount githubAccount = result.data();
        ghAppRepository.save(githubAccount);

        return Result.ok(GithubAccountAttributes.from(githubAccount));
    }
}
