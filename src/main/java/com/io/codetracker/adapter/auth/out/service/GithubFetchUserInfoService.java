package com.io.codetracker.adapter.auth.out.service;

import com.io.codetracker.adapter.auth.out.dto.response.GithubFetchUserInfoResponse;
import com.io.codetracker.adapter.auth.out.dto.response.GithubEmailResponse;
import com.io.codetracker.application.auth.error.GithubFetchUserInfoError;
import com.io.codetracker.application.auth.result.GithubFetchUserInfoResult;
import com.io.codetracker.application.auth.port.out.GithubFetchUserInfoPort;
import com.io.codetracker.common.result.Result;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;

@Service
public class GithubFetchUserInfoService implements GithubFetchUserInfoPort {

    private final RestClient restClient;

    public GithubFetchUserInfoService(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Result<GithubFetchUserInfoResult, GithubFetchUserInfoError> fetch(String accessToken) {
        if (accessToken == null || accessToken.isBlank()) {
            return Result.fail(GithubFetchUserInfoError.INVALID_ACCESS_TOKEN);
        }

        GithubFetchUserInfoResponse githubUser;
        try {
            githubUser = restClient.get()
                    .uri("https://api.github.com/user")
                    .headers(headers -> {
                        headers.setBearerAuth(accessToken);
                        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
                    })
                    .retrieve()
                    .body(GithubFetchUserInfoResponse.class);
        } catch (RestClientException e) {
            return Result.fail(GithubFetchUserInfoError.GITHUB_REQUEST_FAILED);
        }

        if (githubUser == null) {
            return Result.fail(GithubFetchUserInfoError.GITHUB_REQUEST_FAILED);
        }

        String email = githubUser.email();

        if (email == null || email.isBlank()) {
            GithubEmailResponse[] emails;
            try {
                emails = restClient.get()
                        .uri("https://api.github.com/user/emails")
                        .headers(headers -> {
                            headers.setBearerAuth(accessToken);
                            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
                        })
                        .retrieve()
                        .body(GithubEmailResponse[].class);
            } catch (RestClientException e) {
                return Result.fail(GithubFetchUserInfoError.GITHUB_REQUEST_FAILED);
            }

            if (emails == null) {
                return Result.fail(GithubFetchUserInfoError.GITHUB_REQUEST_FAILED);
            }

            String selected = null;

            for (GithubEmailResponse candidate : emails) {
                if (candidate.primary() && candidate.verified()) {
                    selected = candidate.email();
                    break;
                }
            }

            if (selected == null) {
                for (GithubEmailResponse candidate : emails) {
                    if (candidate.verified()) {
                        selected = candidate.email();
                        break;
                    }
                }
            }

            if (selected == null && emails.length > 0) {
                selected = emails[0].email();
            }

            if (selected == null || selected.isBlank()) {
                return Result.fail(GithubFetchUserInfoError.NO_EMAIL_FOUND);
            }

            email = selected;
        }

        GithubFetchUserInfoResult result = new GithubFetchUserInfoResult(
                githubUser.id(),
                githubUser.login(),
                githubUser.repos_url(),
                githubUser.name(),
                email,
                githubUser.avatar_url()
        );

        return Result.ok(result);
    }
}