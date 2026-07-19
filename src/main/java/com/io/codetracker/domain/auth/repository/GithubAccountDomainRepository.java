package com.io.codetracker.domain.auth.repository;

import java.util.UUID;

public interface GithubAccountDomainRepository {
    boolean existsById(UUID githubAccountId);
    boolean existByAuthId(UUID authId);
}
