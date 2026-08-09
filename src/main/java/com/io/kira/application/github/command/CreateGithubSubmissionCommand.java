package com.io.kira.application.github.command;

import com.io.kira.domain.github.valueobject.GithubSubmissionMode;

import java.util.UUID;

public record CreateGithubSubmissionCommand(String accessToken, UUID classroomId, String studentActivityId, String activityId, String repositoryUrl, GithubSubmissionMode mode) {
}