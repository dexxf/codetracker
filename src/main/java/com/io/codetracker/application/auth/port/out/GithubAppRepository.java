package com.io.codetracker.application.auth.port.out;

import java.util.Optional;

import com.io.codetracker.domain.auth.entity.GithubAccount;
import com.io.codetracker.infrastructure.auth.persistence.entity.GithubAccountEntity;

public interface GithubAppRepository {
    void save(GithubAccount githubAccount);
    Optional<GithubAccountEntity> findByAuthId(String authId);
    Optional<GithubAccountEntity> findByGithubId(Long id);
}
