package com.io.codetracker.application.activity.result;

import com.io.codetracker.domain.activity.valueObject.SubmissionStatus;
import com.io.codetracker.domain.github.valueobject.GithubSubmissionMode;

import java.time.LocalDateTime;
import java.util.UUID;

public record StudentActivityInfoData(
        String userId,
        String studentActivityId,
        String activityId,
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String repositoryOwnerUsername,
        String repositoryId,
        String repositoryName,
        GithubSubmissionMode repositoryMode,
        String repositoryUrl,
        LocalDateTime submittedAt,
        SubmissionStatus submissionStatus,
        String feedback
) {
        public StudentActivityInfoData(
            String userId,
            UUID studentActivityId,
            String activityId,
            String title,
            String description,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String repositoryOwnerUsername,
            String repositoryId,
            String repositoryName,
            GithubSubmissionMode repositoryMode,
            String repositoryUrl,
            LocalDateTime submittedAt,
            SubmissionStatus submissionStatus,
            String feedback
    ) {
        this(
                userId,
                studentActivityId != null ? studentActivityId.toString() : null,
                activityId,
                title,
                description,
                createdAt,
                updatedAt,
                repositoryOwnerUsername,
                repositoryId,
                repositoryName,
                repositoryMode,
                repositoryUrl,
                                submittedAt,
                                submissionStatus,
                                feedback
        );
    }
}
