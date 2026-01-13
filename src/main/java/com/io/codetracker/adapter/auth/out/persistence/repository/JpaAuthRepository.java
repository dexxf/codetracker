package com.io.codetracker.adapter.auth.out.persistence.repository;

import com.io.codetracker.infrastructure.auth.persistence.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaAuthRepository extends JpaRepository<AuthEntity, String>{
    Optional<AuthEntity> findByUsername(String username);
    boolean existsByEmail(String email);
    Optional<AuthEntity> findByEmail(String email);
}