package com.io.codetracker.domain.auth.entity;

public final class GithubAccount {

    private final String githubAccountId;
    private final String authId;
    private final Long githubId;
    private final String accessToken;

    public GithubAccount(String githubAccountId,String authId, Long githubId, String accessToken) {
        this.githubAccountId = githubAccountId;
        this.authId = authId;
        this.githubId = githubId;
        this.accessToken = accessToken;
    }

    public String getGithubAccountId() {
        return githubAccountId;
    }
    
    public String getAuthId() {
        return authId;
    }

    public Long getGithubId() {
        return githubId;
    }

    public String getAccessToken() {
        return accessToken;
    }

}
