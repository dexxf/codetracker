package com.io.kira.infrastructure.auth.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.io.kira.infrastructure.auth.persistence.entity.GithubAccountEntity;

import java.util.UUID;

public interface JpaGithubAccountRepository extends JpaRepository<GithubAccountEntity, UUID> {
}
