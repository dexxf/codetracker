package com.io.kira.application.github.port.out;

import com.io.kira.application.github.result.GithubRepositoryData;

import java.util.Optional;

public interface GithubSubmissionIntegrationPort {
	Optional<GithubRepositoryData> findByRepository(String accessToken, String repositoryUrl);
}
