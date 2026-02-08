package com.io.codetracker.domain.auth.repository;

public interface GithubAccountDomainRepository {
    boolean existsById(String githubAccountId);
    boolean existByAuthId(String authId);
}
