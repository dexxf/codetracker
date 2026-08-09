package com.io.kira.adapter.activity.out.persistence.repository;

import com.io.kira.adapter.auth.out.persistence.mapper.GithubAccountMapper;
import com.io.kira.application.activity.port.out.ActivityGithubAccountAppPort;
import com.io.kira.domain.auth.entity.GithubAccount;
import com.io.kira.infrastructure.auth.persistence.repository.JpaGithubAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class ActivityGithubAccountAppAdapter implements ActivityGithubAccountAppPort {

    private final JpaGithubAccountRepository jpaGithubAccountRepository;

    @Override
    public Optional<GithubAccount> findByAuthId(UUID authId) {
        return jpaGithubAccountRepository.findById(authId)
                .map(GithubAccountMapper::toDomain);
    }
}
