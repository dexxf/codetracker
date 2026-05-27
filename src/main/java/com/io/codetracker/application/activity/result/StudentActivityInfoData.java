package com.io.codetracker.application.activity.result;

import com.io.codetracker.domain.activity.valueObject.SubmissionStatus;
import com.io.codetracker.domain.github.valueobject.GithubSubmissionMode;

import java.time.Instant;
import java.util.UUID;

public record StudentActivityInfoData(
        String userId,
        String studentActivityId,
        String activityId,
        String title,
        String description,
        Integer maxScore,
        Instant createdAt,
        Instant updatedAt,
        String repositoryOwnerUsername,
        String repositoryId,
        String repositoryName,
        GithubSubmissionMode repositoryMode,
        String repositoryUrl,
        Instant submittedAt,
        SubmissionStatus submissionStatus,
        String feedback,
        Integer score
) {
        public StudentActivityInfoData(
            String userId,
            UUID studentActivityId,
            String activityId,
            String title,
            String description,
            Integer maxScore,
            Instant createdAt,
            Instant updatedAt,
            String repositoryOwnerUsername,
            String repositoryId,
            String repositoryName,
            GithubSubmissionMode repositoryMode,
            String repositoryUrl,
            Instant submittedAt,
            SubmissionStatus submissionStatus,
            String feedback,
            Integer score
    ) {
        this(
                userId,
                studentActivityId != null ? studentActivityId.toString() : null,
                activityId,
                title,
                description,
                maxScore,
                createdAt,
                updatedAt,
                repositoryOwnerUsername,
                repositoryId,
                repositoryName,
                repositoryMode,
                repositoryUrl,
                submittedAt,
                submissionStatus,
                feedback,
                score
        );
    }
}
