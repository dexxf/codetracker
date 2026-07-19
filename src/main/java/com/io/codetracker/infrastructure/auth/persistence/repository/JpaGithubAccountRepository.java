package com.io.codetracker.infrastructure.auth.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.io.codetracker.infrastructure.auth.persistence.entity.GithubAccountEntity;

import java.util.UUID;

public interface JpaGithubAccountRepository extends JpaRepository<GithubAccountEntity, UUID> {
}
