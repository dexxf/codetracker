package com.io.codetracker.adapter.auth.out.persistence.repository;

import org.springframework.stereotype.Repository;

import com.io.codetracker.domain.auth.repository.GithubAccountDomainRepository;
import com.io.codetracker.infrastructure.auth.persistence.repository.JpaGithubAccountRepository;

import lombok.AllArgsConstructor;

import java.util.UUID;


@Repository
@AllArgsConstructor
public class GithubAccountDomainRepositoryImpl implements GithubAccountDomainRepository{

    private final JpaGithubAccountRepository repository;

    @Override
    public boolean existsById(UUID githubAccountId) {
        return repository.existsById(githubAccountId);
    }

    @Override
    public boolean existByAuthId(UUID authId) {
        return repository.existsById(authId);
    }
}
