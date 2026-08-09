package com.io.kira.application.github.service;

import com.io.kira.application.github.error.GetGithubRepositoriesError;
import com.io.kira.application.github.port.in.GetGithubRepositoriesUseCase;
import com.io.kira.application.github.port.out.GithubAccountAppPort;
import com.io.kira.application.github.port.out.GithubRepositoryIntegrationPort;
import com.io.kira.application.github.result.GithubRepositoryDetailsData;
import com.io.kira.common.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GithubRepositoryService implements GetGithubRepositoriesUseCase {

    private final GithubAccountAppPort githubAccountAppPort;
    private final GithubRepositoryIntegrationPort githubRepositoryIntegrationPort;

    @Override
    public Result<List<GithubRepositoryDetailsData>, GetGithubRepositoriesError> execute(UUID authId) {
        Optional<com.io.kira.domain.auth.entity.GithubAccount> githubAccountOptional =
                githubAccountAppPort.findByAuthId(authId);

        if (githubAccountOptional.isEmpty()) {
            return Result.fail(GetGithubRepositoriesError.GITHUB_ACCOUNT_NOT_FOUND);
        }

        Optional<List<GithubRepositoryDetailsData>> repositoriesOptional =
                githubRepositoryIntegrationPort.findAllByAccessToken(githubAccountOptional.get().getAccessToken());

        if (repositoriesOptional.isEmpty()) {
            return Result.fail(GetGithubRepositoriesError.REPOSITORIES_FETCH_FAILED);
        }

        return Result.ok(repositoriesOptional.get());
    }
}
