package com.io.codetracker.application.activity.port.out;

import java.util.Optional;

public interface GithubActivityIntegrationPort {
    boolean existsByRepository(String accessToken, String repositoryUrl);
    String createRepository(String accessToken, String repositoryUrl);
    boolean existsByRepositoryName(String accessToken, String repositoryName);
    Optional<String> findLatestCommitSha(String accessToken, String repositoryUrl);
}
