package com.io.codetracker.application.github.result;

import com.io.codetracker.domain.github.entity.GithubSubmission;
import com.io.codetracker.domain.github.valueobject.GithubSubmissionMode;

import java.util.UUID;

public record GithubSubmissionData(
		UUID classroomId,
		String studentActivityId,
		String activityId,
		String repositoryOwnerUsername,
		String repositoryId,
		String repositoryName,
		GithubSubmissionMode mode,
		String repositoryUrl
) {
	public static GithubSubmissionData from(GithubSubmission submission) {
		return new GithubSubmissionData(
				submission.getClassroomId(),
				submission.getStudentActivityId(),
				submission.getActivityId(),
				submission.getRepositoryOwnerUsername(),
				submission.getRepositoryId(),
				submission.getRepositoryName(),
				submission.getMode(),
				submission.getRepositoryUrl()
		);
	}
}
