package com.io.codetracker.domain.auth.entity;

import java.util.Objects;
import java.util.UUID;

public final class GithubAccount {

    private final UUID id;
    private final Long githubId;
    private String accessToken;

    public GithubAccount(UUID id, Long githubId, String accessToken) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.githubId = Objects.requireNonNull(githubId, "githubId must not be null");
        this.accessToken = Objects.requireNonNull(accessToken, "accessToken must not be null");
    }

    public UUID getId() {
        return id;
    }

    public Long getGithubId() {
        return githubId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void updateAccessToken(String accessToken) {
        this.accessToken = Objects.requireNonNull(accessToken, "accessToken must not be null");
    }

}
