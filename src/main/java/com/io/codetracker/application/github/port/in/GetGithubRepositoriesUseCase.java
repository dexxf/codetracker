package com.io.codetracker.application.github.port.in;

import com.io.codetracker.application.github.error.GetGithubRepositoriesError;
import com.io.codetracker.application.github.result.GithubRepositoryDetailsData;
import com.io.codetracker.common.result.Result;

import java.util.List;

public interface GetGithubRepositoriesUseCase {
    Result<List<GithubRepositoryDetailsData>, GetGithubRepositoriesError> execute(String authId);
}
