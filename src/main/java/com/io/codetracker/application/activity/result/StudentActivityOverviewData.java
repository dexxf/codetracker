package com.io.codetracker.application.activity.result;

import com.io.codetracker.domain.activity.valueObject.ActivityStatus;
import com.io.codetracker.domain.activity.valueObject.SubmissionStatus;
import com.io.codetracker.domain.github.valueobject.GithubSubmissionMode;

import java.time.Instant;
import java.util.UUID;

public record StudentActivityOverviewData(
        String activityId,
        String title,
        String description,
        Integer maxScore,
        Instant dueDate,
        String studentActivityId,
        String repositoryName,
        String repositoryUrl,
        GithubSubmissionMode repositoryMode,
        Instant submittedAt,
        String submittedCommitSha,
        SubmissionStatus submissionStatus,
        Integer score,
        String feedback,
        String activityStatus
) {
    public StudentActivityOverviewData(
            String activityId,
            String title,
            String description,
            Integer maxScore,
            Instant dueDate,
            UUID studentActivityId,
            String repositoryName,
            String repositoryUrl,
            GithubSubmissionMode repositoryMode,
            Instant submittedAt,
            String submittedCommitSha,
            SubmissionStatus submissionStatus,
            Integer score,
            String feedback,
            String activityStatus
    ) {
        this(
                activityId,
                title,
                description,
                maxScore,
                dueDate,
                studentActivityId != null ? studentActivityId.toString() : null,
                repositoryName,
                repositoryUrl,
                repositoryMode,
                submittedAt,
                submittedCommitSha,
                submissionStatus,
                score,
                feedback,
                activityStatus
        );
    }

    public StudentActivityOverviewData(
            String activityId,
            String title,
            String description,
            Integer maxScore,
            Instant dueDate,
            UUID studentActivityId,
            String repositoryName,
            String repositoryUrl,
            GithubSubmissionMode repositoryMode,
            Instant submittedAt,
            String submittedCommitSha,
            SubmissionStatus submissionStatus,
            Integer score,
            String feedback,
            ActivityStatus activityStatus
    ) {
        this(
                activityId,
                title,
                description,
                maxScore,
                dueDate,
                studentActivityId,
                repositoryName,
                repositoryUrl,
                repositoryMode,
                submittedAt,
                submittedCommitSha,
                submissionStatus,
                score,
                feedback,
                activityStatus != null ? activityStatus.name() : null
        );
    }
}

