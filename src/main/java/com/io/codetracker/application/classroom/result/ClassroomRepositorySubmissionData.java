package com.io.codetracker.application.classroom.result;

import java.time.LocalDateTime;

public record ClassroomRepositorySubmissionData(
        String studentUserId,
        String firstName,
        String lastName,
        String profileUrl,
        String activityId,
        String activityTitle,
        String repositoryName,
        String repositoryUrl,
        LocalDateTime submittedAt
) {
}
