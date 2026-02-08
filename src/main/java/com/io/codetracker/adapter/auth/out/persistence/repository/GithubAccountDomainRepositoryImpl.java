package com.io.codetracker.adapter.auth.out.persistence.repository;

import org.springframework.stereotype.Repository;

import com.io.codetracker.domain.auth.repository.GithubAccountDomainRepository;

import lombok.AllArgsConstructor;


@Repository
@AllArgsConstructor
public class GithubAccountDomainRepositoryImpl implements GithubAccountDomainRepository{

    private final JpaGithubAccountRepository repository;

    @Override
    public boolean existsById(String githubAccountId) {
        return repository.existsById(githubAccountId);
    }

    @Override
    public boolean existByAuthId(String authId) {
        return repository.existsByAuthId(authId);
    }
}
