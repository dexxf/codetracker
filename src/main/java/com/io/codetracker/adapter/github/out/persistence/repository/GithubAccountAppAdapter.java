package com.io.codetracker.adapter.github.out.persistence.repository;

import com.io.codetracker.adapter.auth.out.persistence.mapper.GithubAccountMapper;
import com.io.codetracker.application.github.port.out.GithubAccountAppPort;
import com.io.codetracker.domain.auth.entity.GithubAccount;
import com.io.codetracker.infrastructure.auth.persistence.repository.JpaGithubAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class GithubAccountAppAdapter implements GithubAccountAppPort {

    private final JpaGithubAccountRepository jpaGithubAccountRepository;

    @Override
    public Optional<GithubAccount> findByAuthId(String authId) {
        return jpaGithubAccountRepository.findByAuthId(authId)
                .map(GithubAccountMapper::toDomain);
    }
}
