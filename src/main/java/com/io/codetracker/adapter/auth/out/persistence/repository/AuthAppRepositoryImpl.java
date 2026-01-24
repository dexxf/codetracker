package com.io.codetracker.adapter.auth.out.persistence.repository;

import com.io.codetracker.adapter.auth.out.persistence.mapper.AuthMapper;
import com.io.codetracker.application.auth.port.out.AuthAppRepository;
import com.io.codetracker.domain.auth.entity.Auth;
import com.io.codetracker.infrastructure.auth.persistence.entity.AuthEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthAppRepositoryImpl implements AuthAppRepository {

    private final JpaAuthRepository jpa;

    public AuthAppRepositoryImpl(@Qualifier("jpaAuthRepository") JpaAuthRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void save(Auth auth) {
        jpa.save(AuthMapper.toEntity(auth));
    }

    @Override
    public boolean emailExists(String email) {
        return jpa.existsByEmail(email);
    }

    @Override
    public Optional<Auth> findByEmail(String email) {
        Optional<AuthEntity> authEntity = jpa.findByEmail(email);
        return authEntity.map(e -> AuthMapper.toDomain(e));
    }

}
