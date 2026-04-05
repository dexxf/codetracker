package com.io.codetracker.adapter.github.out.service;

import com.io.codetracker.application.github.port.out.GithubRepositoryIntegrationPort;
import com.io.codetracker.application.github.result.GithubRepositoryDetailsData;
import com.io.codetracker.application.github.result.GithubRepositoryLicenseData;
import com.io.codetracker.application.github.result.GithubRepositoryOwnerData;
import org.kohsuke.github.GHLicense;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GithubRepositoryIntegrationAdapter implements GithubRepositoryIntegrationPort {

    @Override
    public Optional<List<GithubRepositoryDetailsData>> findAllByAccessToken(String accessToken) {
        try {
            GitHub gitHub = GitHub.connectUsingOAuth(accessToken);
            List<GithubRepositoryDetailsData> repositories = new ArrayList<>();

            for (GHRepository repository : gitHub.getMyself().listRepositories(100)) {
                repositories.add(mapRepository(repository));
            }

            return Optional.of(repositories);
        } catch (IOException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    private GithubRepositoryDetailsData mapRepository(GHRepository repository) {
        return new GithubRepositoryDetailsData(
                repository.getId(),
                repository.getNodeId(),
                repository.getName(),
                repository.getFullName(),
                repository.getDescription(),
                repository.getHomepage(),
                repository.getDefaultBranch(),
                repository.getLanguage(),
                repository.getVisibility() == null ? null : repository.getVisibility().name(),
                toStringUrl(repository.getHtmlUrl()),
                toStringUrl(repository.getUrl()),
                repository.getGitTransportUrl(),
                repository.getSshUrl(),
                repository.getHttpTransportUrl(),
                repository.getSvnUrl(),
                repository.getMirrorUrl(),
                safeOwner(repository),
                safeLicense(repository),
                safeTopics(repository),
                safeLanguages(repository),
                repository.isPrivate(),
                repository.isFork(),
                repository.isArchived(),
                repository.isDisabled(),
                repository.isTemplate(),
                repository.isAllowForking(),
                repository.isAllowMergeCommit(),
                repository.isAllowRebaseMerge(),
                repository.isAllowSquashMerge(),
                repository.isDeleteBranchOnMerge(),
                repository.hasIssues(),
                repository.hasProjects(),
                repository.hasWiki(),
                repository.hasPages(),
                repository.hasDownloads(),
                repository.hasAdminAccess(),
                repository.hasPushAccess(),
                repository.hasPullAccess(),
                repository.getForksCount(),
                repository.getStargazersCount(),
                repository.getWatchersCount(),
                repository.getSubscribersCount(),
                repository.getOpenIssueCount(),
                repository.getSize(),
                safeCreatedAt(repository),
                safeUpdatedAt(repository),
                repository.getPushedAt()
        );
    }

    private GithubRepositoryOwnerData safeOwner(GHRepository repository) {
        try {
            GHUser owner = repository.getOwner();
            return new GithubRepositoryOwnerData(
                    owner.getId(),
                    owner.getNodeId(),
                    owner.getLogin(),
                    owner.getName(),
                    owner.getType(),
                    owner.getAvatarUrl(),
                    toStringUrl(owner.getHtmlUrl()),
                    toStringUrl(owner.getUrl()),
                    owner.isSiteAdmin()
            );
        } catch (IOException e) {
            return null;
        }
    }

    private GithubRepositoryLicenseData safeLicense(GHRepository repository) {
        try {
            GHLicense license = repository.getLicense();
            if (license == null) {
                return null;
            }

            return new GithubRepositoryLicenseData(
                    license.getId(),
                    license.getNodeId(),
                    license.getKey(),
                    license.getName(),
                    license.getSpdxId(),
                    license.getDescription(),
                    toStringUrl(license.getHtmlUrl())
            );
        } catch (IOException e) {
            return null;
        }
    }

    private List<String> safeTopics(GHRepository repository) {
        try {
            return repository.listTopics();
        } catch (IOException e) {
            return List.of();
        }
    }

    private Map<String, Long> safeLanguages(GHRepository repository) {
        try {
            return new LinkedHashMap<>(repository.listLanguages());
        } catch (IOException e) {
            return Map.of();
        }
    }

    private Instant safeCreatedAt(GHRepository repository) {
        try {
            return repository.getCreatedAt();
        } catch (IOException e) {
            return null;
        }
    }

    private Instant safeUpdatedAt(GHRepository repository) {
        try {
            return repository.getUpdatedAt();
        } catch (IOException e) {
            return null;
        }
    }

    private String toStringUrl(java.net.URL url) {
        return url == null ? null : url.toString();
    }
}
