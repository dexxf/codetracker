package com.io.codetracker.adapter.auth.in.mapper;

import com.io.codetracker.application.auth.error.GithubAccountRegistrationError;
import org.springframework.http.HttpStatus;

public final class GithubAccountHttpMapper {

    private GithubAccountHttpMapper() {}

    public static HttpStatus toStatus(GithubAccountRegistrationError error) {
        return switch (error) {
            case ALREADY_LINKED -> HttpStatus.CONFLICT;
            case GITHUB_ID_NOT_FOUND,
                 ACCESS_TOKEN_MISSING -> HttpStatus.BAD_REQUEST;
        };
    }

    public static String toMessage(GithubAccountRegistrationError error) {
        return switch (error) {
            case GITHUB_ID_NOT_FOUND -> "GitHub ID not found.";
            case ACCESS_TOKEN_MISSING -> "Access token is missing.";
            case ALREADY_LINKED -> "GitHub account is already linked.";
        };
    }
}