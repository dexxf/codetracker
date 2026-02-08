package com.io.codetracker.adapter.auth.out.persistence.repository;


import org.springframework.stereotype.Repository;

import com.io.codetracker.adapter.auth.out.persistence.mapper.GithubAccountMapper;
import com.io.codetracker.application.auth.port.out.GithubAppRepository;
import com.io.codetracker.domain.auth.entity.GithubAccount;

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

}
