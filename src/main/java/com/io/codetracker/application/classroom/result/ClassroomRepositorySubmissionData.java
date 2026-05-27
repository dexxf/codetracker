package com.io.codetracker.application.classroom.result;

import java.time.Instant;

public record ClassroomRepositorySubmissionData(
        String studentUserId,
        String firstName,
        String lastName,
        String profileUrl,
        String activityId,
        String activityTitle,
        String repositoryName,
        String repositoryUrl,
        Instant submittedAt
) {
}
