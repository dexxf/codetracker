package com.io.codetracker.infrastructure.github.persistence.repository;

import com.io.codetracker.infrastructure.github.persistence.entity.GithubSubmissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaGithubSubmissionRepository extends JpaRepository<GithubSubmissionEntity, Long> {
}