package com.io.codetracker.application.activity.result;

import com.io.codetracker.domain.activity.entity.StudentActivity;
import java.util.UUID;
import com.io.codetracker.domain.activity.valueObject.SubmissionStatus;

public record StudentActivitySubmissionData(
        String activityId,
        UUID userId,
        SubmissionStatus submissionStatus,
        String feedback,
        Integer score,
        String submittedCommitSha
) {
    public static StudentActivitySubmissionData from(StudentActivity studentActivity) {
        return new StudentActivitySubmissionData(
                studentActivity.getActivityId(),
                studentActivity.getUserId(),
                studentActivity.getSubmissionStatus(),
                studentActivity.getFeedback(),
                studentActivity.getScore(),
                studentActivity.getSubmittedCommitSha()
        );
    }
}

