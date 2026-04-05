package com.io.codetracker.adapter.github.in.mapper;

import com.io.codetracker.application.github.error.GetGithubRepositoriesError;
import org.springframework.http.HttpStatus;

public final class GetGithubRepositoriesHttpMapper {

    private GetGithubRepositoriesHttpMapper() {
    }

    public static HttpStatus toStatus(GetGithubRepositoriesError error) {
        return switch (error) {
            case GITHUB_ACCOUNT_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case REPOSITORIES_FETCH_FAILED -> HttpStatus.BAD_GATEWAY;
        };
    }

    public static String toMessage(GetGithubRepositoriesError error) {
        return switch (error) {
            case GITHUB_ACCOUNT_NOT_FOUND -> "Github account not found";
            case REPOSITORIES_FETCH_FAILED -> "Failed to fetch github repositories";
        };
    }
}
