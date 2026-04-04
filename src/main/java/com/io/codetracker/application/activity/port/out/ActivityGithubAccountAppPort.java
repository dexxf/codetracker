package com.io.codetracker.application.activity.port.out;

import com.io.codetracker.domain.auth.entity.GithubAccount;

import java.util.Optional;

public interface ActivityGithubAccountAppPort {
    Optional<GithubAccount> findByAuthId(String authId);
}
