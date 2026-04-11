package com.io.codetracker.application.activity.result;

import com.io.codetracker.domain.activity.entity.StudentActivity;
import com.io.codetracker.domain.activity.valueObject.SubmissionStatus;

public record StudentActivityData(
        String activityId,
        String userId,
        SubmissionStatus submissionStatus,
        String feedback,
        Integer score
) {
    public static StudentActivityData from(StudentActivity studentActivity) {
        return new StudentActivityData(
                studentActivity.getActivityId(),
                studentActivity.getUserId(),
                studentActivity.getSubmissionStatus(),
                studentActivity.getFeedback(),
                studentActivity.getScore()
        );
    }
}
