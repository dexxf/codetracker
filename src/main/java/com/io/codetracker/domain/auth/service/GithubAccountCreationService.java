package com.io.codetracker.domain.auth.service;


import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.auth.entity.GithubAccount;
import com.io.codetracker.domain.auth.factory.GithubAccountFactory;
import com.io.codetracker.domain.auth.repository.GithubAccountDomainRepository;
import com.io.codetracker.domain.auth.result.GithubAccountCreationResult;


public class GithubAccountCreationService {
    
    private final GithubAccountDomainRepository repository;
    private final GithubAccountFactory githubAccountFactory;

    public GithubAccountCreationService(GithubAccountDomainRepository repository, GithubAccountFactory githubAccountFactory) {
        this.repository = repository;
        this.githubAccountFactory = githubAccountFactory;
    }
    
    public Result<GithubAccount, GithubAccountCreationResult> create(String authId, Long githubId, String accessToken) {
  
    if (githubId == null) {
        return Result.fail(GithubAccountCreationResult.GITHUB_ID_NOT_FOUND);
    }   

    if (accessToken == null || accessToken.isBlank()) {
        return Result.fail(GithubAccountCreationResult.ACCESS_TOKEN_MISSING);
    }

    if (repository.existByAuthId(authId)) {
        return Result.fail(GithubAccountCreationResult.ALREADY_LINKED);
    }

    GithubAccount entity = githubAccountFactory.create(authId, githubId, accessToken);
    return Result.ok(entity);
    }
}
