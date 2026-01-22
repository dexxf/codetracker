package com.io.codetracker.domain.auth.repository;

public interface AuthDomainRepository {
    boolean existsByUsername(String username);
    boolean existsById(String authId);
}
