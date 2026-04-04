package com.io.codetracker.adapter.activity.out.persistence.repository;

import com.io.codetracker.adapter.auth.out.persistence.mapper.GithubAccountMapper;
import com.io.codetracker.application.activity.port.out.ActivityGithubAccountAppPort;
import com.io.codetracker.domain.auth.entity.GithubAccount;
import com.io.codetracker.infrastructure.auth.persistence.repository.JpaGithubAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class ActivityGithubAccountAppAdapter implements ActivityGithubAccountAppPort {

    private final JpaGithubAccountRepository jpaGithubAccountRepository;

    @Override
    public Optional<GithubAccount> findByAuthId(String authId) {
        return jpaGithubAccountRepository.findByAuthId(authId)
                .map(GithubAccountMapper::toDomain);
    }
}
