package com.io.codetracker.infrastructure.auth.persistence.repository;

import com.io.codetracker.infrastructure.auth.persistence.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaAuthRepository extends JpaRepository<AuthEntity, String>{
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<AuthEntity> findByUserId(String userId);
    Optional<AuthEntity> findByGithubAccountEntity_GithubId(Long githubAccountEntityGithubId);
}
