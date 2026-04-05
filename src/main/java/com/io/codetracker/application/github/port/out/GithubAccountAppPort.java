package com.io.codetracker.application.github.port.out;

import com.io.codetracker.domain.auth.entity.GithubAccount;

import java.util.Optional;

public interface GithubAccountAppPort {
    Optional<GithubAccount> findByAuthId(String authId);
}
