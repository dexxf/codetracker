package com.io.codetracker.application.activity.port.out;

import com.io.codetracker.domain.auth.entity.GithubAccount;

import java.util.Optional;
import java.util.UUID;

public interface ActivityGithubAccountAppPort {
    Optional<GithubAccount> findByAuthId(UUID authId);
}
