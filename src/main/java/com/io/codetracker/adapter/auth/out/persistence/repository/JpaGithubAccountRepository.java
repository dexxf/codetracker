package com.io.codetracker.adapter.auth.out.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.io.codetracker.infrastructure.auth.persistence.entity.GithubAccountEntity;

public interface JpaGithubAccountRepository extends JpaRepository<GithubAccountEntity, String> {
    Optional<GithubAccountEntity> findByAuthId(String authId);
    Optional<GithubAccountEntity> findByGithubId(Long githubId);
    boolean existsByAuthId(String authId);
}