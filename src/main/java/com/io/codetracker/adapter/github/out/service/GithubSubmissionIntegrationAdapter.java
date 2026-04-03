package com.io.codetracker.adapter.github.out.service;

import com.io.codetracker.application.github.port.out.GithubSubmissionIntegrationPort;
import com.io.codetracker.application.github.result.GithubRepositoryData;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class GithubSubmissionIntegrationAdapter implements GithubSubmissionIntegrationPort {

    @Override
    public Optional<GithubRepositoryData> findByRepository(String accessToken, String repositoryUrl) {
        try {
            GitHub gitHub = GitHub.connectUsingOAuth(accessToken);
            String repositoryFullName = normalizeRepositoryFullName(gitHub, repositoryUrl);
            GHRepository repository = gitHub.getRepository(repositoryFullName);
            String repositoryOwnerUsername = repositoryFullName.substring(0, repositoryFullName.indexOf('/'));
            return Optional.of(new GithubRepositoryData(
                    repositoryOwnerUsername,
                    String.valueOf(repository.getId()),
                    repository.getName()
            ));
        } catch (IOException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private String normalizeRepositoryFullName(GitHub gitHub, String repositoryUrl) throws IOException {
        String trimmed = repositoryUrl == null ? null : repositoryUrl.trim();

        if (trimmed == null || trimmed.isBlank()) {
            throw new IllegalArgumentException("Repository URL is blank");
        }

        if (trimmed.startsWith("http://") || trimmed.startsWith("https://")) {
            String withoutProtocol = trimmed.replace("https://github.com/", "").replace("http://github.com/", "");
            return stripRepositoryPath(withoutProtocol);
        }

        String cleaned = stripRepositoryPath(trimmed);
        if (cleaned.contains("/")) {
            return cleaned;
        }

        return gitHub.getMyself().getLogin() + "/" + cleaned;
    }

    private String stripRepositoryPath(String repositoryPath) {
        String cleaned = repositoryPath == null ? "" : repositoryPath.trim();
        if (cleaned.endsWith("/")) {
            cleaned = cleaned.substring(0, cleaned.length() - 1);
        }
        if (cleaned.endsWith(".git")) {
            cleaned = cleaned.substring(0, cleaned.length() - 4);
        }
        return cleaned;
    }
}