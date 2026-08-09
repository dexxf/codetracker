package com.io.kira.application.github.port.out;

import com.io.kira.domain.github.entity.GithubSubmission;

public interface GithubSubmissionAppRepository {
	GithubSubmission save(GithubSubmission githubSubmission);
}
