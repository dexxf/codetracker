package com.io.codetracker.application.auth.service;

import com.io.codetracker.application.auth.command.GithubRegistrationCommand;
import org.springframework.stereotype.Service;

import com.io.codetracker.application.auth.port.out.GithubAppRepository;
import com.io.codetracker.application.auth.response.GithubRegistrationResponseDTO;
import com.io.codetracker.application.auth.result.GithubAccountAttributes;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.auth.entity.GithubAccount;
import com.io.codetracker.domain.auth.result.GithubAccountCreationResult;
import com.io.codetracker.domain.auth.service.GithubAccountCreationService;

import java.util.Map;

@Service
public class GithubAccountRegistrationService {

    private final GithubAccountCreationService ghCreationService;
    private final GithubAppRepository ghAppRepository;

    public GithubAccountRegistrationService(GithubAccountCreationService ghCreationService, GithubAppRepository ghAppRepository) {
        this.ghCreationService = ghCreationService;
        this.ghAppRepository = ghAppRepository;
    }

    public GithubRegistrationResponseDTO registerGithubAccount(GithubRegistrationCommand command) {

        Result<GithubAccount, GithubAccountCreationResult> result =
            ghCreationService.create(command.authId(), command.githubId(), command.accessToken());

        if (!result.success()) {
            return GithubRegistrationResponseDTO.failure(result.error().getMessage());
        }

        GithubAccount githubAccount = result.data();
        ghAppRepository.save(githubAccount);

        return GithubRegistrationResponseDTO.success(Map.of(
                GithubAccountAttributes.AUTH_ID, githubAccount.getAuthId(),
                GithubAccountAttributes.GITHUB_ID, githubAccount.getGithubId(),
                GithubAccountAttributes.ACCESS_TOKEN, githubAccount.getAccessToken()
        ));
    }
}
