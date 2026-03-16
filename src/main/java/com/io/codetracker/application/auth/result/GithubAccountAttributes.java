package com.io.codetracker.application.auth.result;

import com.io.codetracker.domain.auth.entity.GithubAccount;

public record GithubAccountAttributes(
        String authId,
        Long githubId,
        String accessToken
) {

    public static GithubAccountAttributes from(GithubAccount githubAccount) {
        return new GithubAccountAttributes(githubAccount.getAuthId(), githubAccount.getGithubId(), githubAccount.getAccessToken());
    }}