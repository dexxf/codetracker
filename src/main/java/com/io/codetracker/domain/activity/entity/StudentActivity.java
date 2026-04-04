package com.io.codetracker.domain.activity.entity;

import com.io.codetracker.domain.activity.valueObject.SubmissionStatus;

public final class StudentActivity {

    private final String studentActivityId;
    private final String activityId;
    private final String userId;
    private SubmissionStatus submissionStatus;
    private String feedback;

    public StudentActivity(String studentActivityId, String activityId, String userId, SubmissionStatus submissionStatus, String feedback) {
        this.studentActivityId = studentActivityId;
        this.activityId = activityId;
        this.userId = userId;
        this.submissionStatus = submissionStatus;
        this.feedback = normalizeFeedback(feedback);
    }

    public StudentActivity(String activityId, String userId, SubmissionStatus submissionStatus, String feedback) {
        this(null, activityId, userId, submissionStatus, feedback);
    }

    // created createNew bc making factory or service is too much.. it only has 4 attributes
    public static StudentActivity createNew(String activityId, String userId) {
        return new StudentActivity(null, activityId, userId, SubmissionStatus.PENDING, null);
    }

    public String getStudentActivityId() {
        return studentActivityId;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getUserId() {
        return userId;
    }

    public SubmissionStatus getSubmissionStatus() {
        return submissionStatus;
    }

    public String getFeedback() {
        return feedback;
    }

    public void markPending() {
        if (submissionStatus == SubmissionStatus.PENDING) {
            throw new IllegalStateException("Submission is already pending.");
        }
        if (submissionStatus == SubmissionStatus.GRADED) {
            throw new IllegalStateException("Graded submission cannot be moved back to pending.");
        }

        this.submissionStatus = SubmissionStatus.PENDING;
        this.feedback = null;
    }

    public void submit() {
        if (submissionStatus == SubmissionStatus.SUBMITTED) {
            throw new IllegalStateException("Submission is already submitted.");
        }
        if (submissionStatus == SubmissionStatus.GRADED) {
            throw new IllegalStateException("Graded submission cannot be submitted again.");
        }

        this.submissionStatus = SubmissionStatus.SUBMITTED;
    }

    public void grade(String feedback) {
        if (submissionStatus != SubmissionStatus.SUBMITTED) {
            throw new IllegalStateException("Only submitted work can be graded.");
        }

        this.submissionStatus = SubmissionStatus.GRADED;
        this.feedback = normalizeFeedback(feedback);
    }

    public void clearFeedback() {
        if (submissionStatus == SubmissionStatus.GRADED) {
            throw new IllegalStateException("Cannot clear feedback from a graded submission.");
        }

        this.feedback = null;
    }

    private String normalizeFeedback(String feedback) {
        if (feedback == null) {
            return null;
        }

        String trimmed = feedback.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
