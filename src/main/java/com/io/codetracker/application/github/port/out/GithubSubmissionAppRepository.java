package com.io.codetracker.application.github.port.out;

import com.io.codetracker.domain.github.entity.GithubSubmission;

public interface GithubSubmissionAppRepository {
	GithubSubmission save(GithubSubmission githubSubmission);
}
