package com.io.codetracker.application.github.port.out;

import com.io.codetracker.domain.auth.entity.GithubAccount;

import java.util.Optional;
import java.util.UUID;

public interface GithubAccountAppPort {
    Optional<GithubAccount> findByAuthId(UUID authId);
}
