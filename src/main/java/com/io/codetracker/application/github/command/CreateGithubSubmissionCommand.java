package com.io.codetracker.application.github.command;

import com.io.codetracker.domain.github.valueobject.GithubSubmissionMode;

public record CreateGithubSubmissionCommand(String accessToken, String classroomId, String studentActivityId, String activityId, String repositoryUrl, GithubSubmissionMode mode) {
}