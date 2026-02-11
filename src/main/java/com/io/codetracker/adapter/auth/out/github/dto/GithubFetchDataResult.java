package com.io.codetracker.adapter.auth.out.github.dto;

public record GithubFetchDataResult(
        boolean success,
        GithubUserInfoDTO githubUserInfoDTO,
        String message
) {

    public static GithubFetchDataResult success(GithubUserInfoDTO user) {
        return new GithubFetchDataResult(true, user, null);
    }

    public static GithubFetchDataResult failure(String message) {
        return new GithubFetchDataResult(false, null, message);
    }
}
