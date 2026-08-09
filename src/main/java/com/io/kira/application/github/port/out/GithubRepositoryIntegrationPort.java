package com.io.kira.application.github.port.out;

import com.io.kira.application.github.result.GithubRepositoryDetailsData;

import java.util.List;
import java.util.Optional;

public interface GithubRepositoryIntegrationPort {
    Optional<List<GithubRepositoryDetailsData>> findAllByAccessToken(String accessToken);
}
