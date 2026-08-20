package com.io.kira.application.activity.result;

import com.io.kira.domain.activity.entity.StudentActivity;
import java.util.UUID;
import com.io.kira.domain.activity.valueObject.SubmissionStatus;

public record StudentActivitySubmissionData(
        UUID activityId,
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

