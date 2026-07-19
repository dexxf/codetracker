package com.io.codetracker.infrastructure.auth.persistence.repository;

import com.io.codetracker.infrastructure.auth.persistence.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaAuthRepository extends JpaRepository<AuthEntity, String>{
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<AuthEntity> findByUserId(UUID userId);
    Optional<AuthEntity> findByGithubAccountEntity_GithubId(Long githubAccountEntityGithubId);
}

