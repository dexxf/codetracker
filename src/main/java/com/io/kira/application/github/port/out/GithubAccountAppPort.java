package com.io.kira.application.github.port.out;

import com.io.kira.domain.auth.entity.GithubAccount;

import java.util.Optional;
import java.util.UUID;

public interface GithubAccountAppPort {
    Optional<GithubAccount> findByAuthId(UUID authId);
}
