package com.io.codetracker.application.auth.port.out;

import com.io.codetracker.domain.auth.entity.Auth;

import java.util.Optional;

public interface AuthAppRepository {
    void save(Auth auth);
    boolean emailExists(String email);
    Optional<Auth> findByEmail(String email);
}