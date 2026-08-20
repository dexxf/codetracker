package com.io.kira.domain.activity.entity;

import com.io.kira.domain.activity.valueObject.SubmissionStatus;
import java.util.UUID;

public final class StudentActivity {

    private final UUID studentActivityId;
    private final UUID activityId;
    private final UUID userId;
    private SubmissionStatus submissionStatus;
    private String feedback;
    private Integer score;
    private String submittedCommitSha;

    private StudentActivity(UUID studentActivityId, UUID activityId, UUID userId, SubmissionStatus submissionStatus, String feedback, Integer score, String submittedCommitSha) {
        this.studentActivityId = studentActivityId;
        this.activityId = activityId;
        this.userId = userId;
        this.submissionStatus = submissionStatus;
        this.feedback = normalizeFeedback(feedback);
        this.score = score;
        this.submittedCommitSha = submittedCommitSha;
    }

    public static StudentActivity createNew(UUID activityId, UUID userId) {
        return new StudentActivity(UUID.randomUUID(), activityId, userId, SubmissionStatus.PENDING, null, null, null);
    }

    public static StudentActivity reconstitute(UUID studentActivityId, UUID activityId, UUID userId, SubmissionStatus submissionStatus, String feedback, Integer score, String submittedCommitSha) {
        return new StudentActivity(studentActivityId, activityId, userId, submissionStatus, feedback, score, submittedCommitSha);
    }

    public UUID getStudentActivityId() {
        return studentActivityId;
    }

    public UUID getActivityId() {
        return activityId;
    }

    public UUID getUserId() {
        return userId;
    }

    public SubmissionStatus getSubmissionStatus() {
        return submissionStatus;
    }

    public String getFeedback() {
        return feedback;
    }

    public Integer getScore() {
        return score;
    }

    public String getSubmittedCommitSha() {
        return submittedCommitSha;
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
        this.score = null;
    }

    public void submit(String commitSha) {
        if (submissionStatus == SubmissionStatus.SUBMITTED) {
            throw new IllegalStateException("Submission is already submitted.");
        }
        if (submissionStatus == SubmissionStatus.GRADED) {
            throw new IllegalStateException("Graded submission cannot be submitted again.");
        }
        if (commitSha == null || commitSha.isBlank()) {
            throw new IllegalArgumentException("A commit SHA is required to submit activity.");
        }

        this.submissionStatus = SubmissionStatus.SUBMITTED;
        this.submittedCommitSha = commitSha;
    }

    public void grade(String feedback, Integer score) {
        if (submissionStatus != SubmissionStatus.SUBMITTED) {
            throw new IllegalStateException("Only submitted work can be graded.");
        }

        if (score == null || score < 0) {
            throw new IllegalArgumentException("Score must be a non-negative number.");
        }

        this.submissionStatus = SubmissionStatus.GRADED;
        this.feedback = normalizeFeedback(feedback);
        this.score = score;
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

