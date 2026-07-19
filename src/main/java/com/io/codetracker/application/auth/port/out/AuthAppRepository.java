package com.io.codetracker.application.auth.port.out;

import com.io.codetracker.domain.auth.aggregate.AuthAccountAggregate;
import com.io.codetracker.domain.auth.entity.Auth;

import java.util.Optional;
import java.util.UUID;

public interface AuthAppRepository {
    void save(AuthAccountAggregate aggregate);
    boolean emailExists(String email);
    boolean existsByUsername(String username);
    Optional<Auth> findByAuthId(UUID authId);
    boolean existsByAuthId(UUID authId);
    Optional<AuthAccountAggregate> findByGithubId(Long aLong);
    Optional<Auth> findByEmail(String email);
}
