package com.io.codetracker.adapter.auth.out.persistence.repository;


import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.io.codetracker.adapter.auth.out.persistence.mapper.GithubAccountMapper;
import com.io.codetracker.application.auth.port.out.GithubAppRepository;
import com.io.codetracker.domain.auth.entity.GithubAccount;
import com.io.codetracker.infrastructure.auth.persistence.repository.JpaGithubAccountRepository;

@Repository
public class GithubAppRepositoryImpl implements GithubAppRepository {

    private final JpaGithubAccountRepository jpaGithubAccountRepository;

    public GithubAppRepositoryImpl(JpaGithubAccountRepository jpaGithubAccountRepository) {
        this.jpaGithubAccountRepository = jpaGithubAccountRepository;
    }

    @Override
    public void save(GithubAccount githubAccount) {
        jpaGithubAccountRepository.save(GithubAccountMapper.toEntity(githubAccount));
    }

    @Override
    public Optional<GithubAccount> findByAuthId(String authId) {
        return jpaGithubAccountRepository.findByAuthId(authId)
                .map(GithubAccountMapper::toDomain);
    }

    @Override
    public Optional<GithubAccount> findByGithubId(Long id) {
        return jpaGithubAccountRepository.findByGithubId(id)
                .map(GithubAccountMapper::toDomain);
    }
}
