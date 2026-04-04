package com.io.codetracker.application.github.port.out;

import com.io.codetracker.application.github.result.GithubRepositoryData;

import java.util.Optional;

public interface GithubSubmissionIntegrationPort {
	Optional<GithubRepositoryData> findByRepository(String accessToken, String repositoryUrl);
}
