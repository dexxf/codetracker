package com.io.kira.application.activity.result;

import com.io.kira.domain.activity.valueObject.SubmissionStatus;
import com.io.kira.domain.github.valueobject.GithubSubmissionMode;

import java.time.Instant;
import java.util.UUID;

public record StudentSubmissionDetailsData(
        UUID userId, UUID studentActivityId, UUID activityId, String title, String description,
        Integer maxScore, Instant createdAt, Instant updatedAt, String repositoryOwnerUsername,
        String repositoryId, String repositoryName, GithubSubmissionMode repositoryMode, String repositoryUrl,
        Instant submittedAt, String submittedCommitSha, SubmissionStatus submissionStatus, String feedback,
        Integer score
) {
}