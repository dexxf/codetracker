package com.io.codetracker.domain.github.entity;

import com.io.codetracker.domain.github.valueobject.GithubSubmissionMode;

import java.time.Instant;

public class GithubSubmission {

        private final String classroomId;
        private final String studentActivityId;
        private final String activityId;
        private String repositoryOwnerUsername;
        private String repositoryId;
        private String repositoryName;
        private GithubSubmissionMode mode;
        private String repositoryUrl;
        private Instant submittedAt;

    public GithubSubmission(String classroomId, String studentActivityId, String activityId, String repositoryOwnerUsername, String repositoryId, String repositoryName, GithubSubmissionMode mode, String repositoryUrl, Instant submittedAt) {
        this.classroomId = classroomId;
        this.studentActivityId = studentActivityId;
        this.activityId = activityId;
        this.repositoryOwnerUsername = repositoryOwnerUsername;
        this.repositoryId = repositoryId;
        this.repositoryName = repositoryName;
        this.mode = mode;
        this.repositoryUrl = repositoryUrl;
        this.submittedAt = submittedAt;
    }

    public static GithubSubmission createNew(String classroomId, String studentActivityId, String activityId, String repositoryOwnerUsername, String repositoryId, String repositoryName, GithubSubmissionMode mode, String repositoryUrl) {
        return new GithubSubmission
                (classroomId, studentActivityId, activityId,
                repositoryOwnerUsername, repositoryId, repositoryName, mode, repositoryUrl, Instant.now());
    }

    public String getClassroomId() {
        return classroomId;
    }

    public String getStudentActivityId() {
        return studentActivityId;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getRepositoryOwnerUsername() {
        return repositoryOwnerUsername;
    }

    public void setRepositoryOwnerUsername(String repositoryOwnerUsername) {
        this.repositoryOwnerUsername = repositoryOwnerUsername;
    }

    public String getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public GithubSubmissionMode getMode() {
        return mode;
    }

    public void setMode(GithubSubmissionMode mode) {
        this.mode = mode;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Instant submittedAt) {
        this.submittedAt = submittedAt;
    }
}
