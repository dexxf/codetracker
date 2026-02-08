package com.io.codetracker.domain.auth.factory;

import com.io.codetracker.domain.auth.entity.GithubAccount;

public interface GithubAccountFactory {
    GithubAccount create(String authId, Long githubId, String accessToken);
}
