package com.io.codetracker.domain.auth.entity;

import java.util.Objects;

public final class GithubAccount {

    private final String id;
    private final Long githubId;
    private String accessToken;

    public GithubAccount(String id, Long githubId, String accessToken) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.githubId = Objects.requireNonNull(githubId, "githubId must not be null");
        this.accessToken = Objects.requireNonNull(accessToken, "accessToken must not be null");
    }

    public String getId() {
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
