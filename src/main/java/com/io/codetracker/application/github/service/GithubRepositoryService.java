package com.io.codetracker.application.github.service;

import com.io.codetracker.application.github.error.GetGithubRepositoriesError;
import com.io.codetracker.application.github.port.in.GetGithubRepositoriesUseCase;
import com.io.codetracker.application.github.port.out.GithubAccountAppPort;
import com.io.codetracker.application.github.port.out.GithubRepositoryIntegrationPort;
import com.io.codetracker.application.github.result.GithubRepositoryDetailsData;
import com.io.codetracker.common.result.Result;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GithubRepositoryService implements GetGithubRepositoriesUseCase {

    private final GithubAccountAppPort githubAccountAppPort;
    private final GithubRepositoryIntegrationPort githubRepositoryIntegrationPort;

    @Override
    public Result<List<GithubRepositoryDetailsData>, GetGithubRepositoriesError> execute(String authId) {
        Optional<com.io.codetracker.domain.auth.entity.GithubAccount> githubAccountOptional =
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
