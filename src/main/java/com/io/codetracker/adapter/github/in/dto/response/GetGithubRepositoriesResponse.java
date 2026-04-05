package com.io.codetracker.adapter.github.in.dto.response;

import com.io.codetracker.application.github.result.GithubRepositoryDetailsData;

import java.util.List;

public record GetGithubRepositoriesResponse(
        List<GithubRepositoryDetailsData> data,
        String message
) {

    public static GetGithubRepositoriesResponse ok(List<GithubRepositoryDetailsData> data) {
        return new GetGithubRepositoriesResponse(data, "Success");
    }

    public static GetGithubRepositoriesResponse fail(String message) {
        return new GetGithubRepositoriesResponse(List.of(), message);
    }
}
