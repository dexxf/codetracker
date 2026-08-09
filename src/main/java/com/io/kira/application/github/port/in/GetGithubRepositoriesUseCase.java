package com.io.kira.application.github.port.in;

import com.io.kira.application.github.error.GetGithubRepositoriesError;
import com.io.kira.application.github.result.GithubRepositoryDetailsData;
import com.io.kira.common.result.Result;

import java.util.List;
import java.util.UUID;

public interface GetGithubRepositoriesUseCase {
    Result<List<GithubRepositoryDetailsData>, GetGithubRepositoriesError> execute(UUID authId);
}
