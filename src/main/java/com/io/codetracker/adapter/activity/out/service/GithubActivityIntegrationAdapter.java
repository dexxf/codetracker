package com.io.codetracker.adapter.activity.out.service;

import com.io.codetracker.application.activity.port.out.GithubActivityIntegrationPort;
import org.kohsuke.github.GHFileNotFoundException;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GithubActivityIntegrationAdapter implements GithubActivityIntegrationPort {

    @Override
    public boolean existsByRepository(String accessToken, String repositoryUrl) {
        try {
            GitHub gitHub = GitHub.connectUsingOAuth(accessToken);
            String repositoryFullName = normalizeRepositoryFullName(gitHub, repositoryUrl);
            gitHub.getRepository(repositoryFullName);
            return true;
        } catch (IOException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public boolean existsByRepositoryName(String accessToken, String repositoryName) {
        try {
            GitHub gitHub = GitHub.connectUsingOAuth(accessToken);
            String repositoryFullName = gitHub.getMyself().getLogin() + "/" + repositoryName;
            gitHub.getRepository(repositoryFullName);
            return true;
        } catch (IOException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String createRepository(String accessToken, String repositoryUrl) {
        try {
            GitHub gitHub = GitHub.connectUsingOAuth(accessToken);
            String owner = gitHub.getMyself().getLogin();
            String repositoryName = extractRepositoryName(repositoryUrl);

            if (repositoryName.isBlank()) {
                return null;
            }

            String repositoryFullName = owner + "/" + repositoryName;

            try {
                gitHub.getRepository(repositoryFullName);
                return null;
            } catch (GHFileNotFoundException ignored) {
            }

            GHRepository repository = gitHub.createRepository(repositoryName)
                    .description("Repository created by CodeTracker")
                    .private_(false)
                    .autoInit(true)
                    .create();

            return repository.getHtmlUrl().toString();
        } catch (IOException | IllegalArgumentException e) {
            return null;
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

    private String extractRepositoryName(String repositoryUrl) {
        String cleaned = stripRepositoryPath(repositoryUrl == null ? "" : repositoryUrl.trim());
        if (cleaned.isBlank()) {
            return cleaned;
        }

        int lastSlashIndex = cleaned.lastIndexOf('/');
        return lastSlashIndex >= 0 ? cleaned.substring(lastSlashIndex + 1) : cleaned;
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
