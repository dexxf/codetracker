package com.io.codetracker.adapter.github.out.persistence.mapper;

import com.io.codetracker.domain.github.entity.GithubSubmission;
import com.io.codetracker.infrastructure.github.persistence.entity.GithubSubmissionEntity;

public final class GithubSubmissionMapper {

    private GithubSubmissionMapper() {
    }

    public static GithubSubmissionEntity toEntity(GithubSubmission domain) {
        if (domain == null) {
            return null;
        }

        GithubSubmissionEntity entity = new GithubSubmissionEntity();
        entity.setRepositoryOwnerUsername(domain.getRepositoryOwnerUsername());
        entity.setRepositoryId(domain.getRepositoryId());
        entity.setRepositoryName(domain.getRepositoryName());
        entity.setMode(domain.getMode());
        entity.setRepositoryUrl(domain.getRepositoryUrl());
        entity.setSubmittedAt(domain.getSubmittedAt());
        return entity;
    }

    public static GithubSubmission toDomain(GithubSubmissionEntity entity) {
        if (entity == null) {
            return null;
        }

        return new GithubSubmission(
            entity.getStudentActivity().getActivityEntity().getClassroomEntity().getClassroomId(),
                entity.getStudentActivity().getStudentActivityId().toString(),
                entity.getStudentActivity().getActivityEntity().getActivityId(),
                entity.getRepositoryOwnerUsername(),
                entity.getRepositoryId(),
                entity.getRepositoryName(),
                entity.getMode(),
                entity.getRepositoryUrl(),
                entity.getSubmittedAt()
        );
    }
}