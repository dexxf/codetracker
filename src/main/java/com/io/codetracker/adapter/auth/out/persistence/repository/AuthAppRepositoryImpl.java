package com.io.codetracker.adapter.auth.out.persistence.repository;

import com.io.codetracker.adapter.auth.out.persistence.mapper.AuthMapper;
import com.io.codetracker.application.auth.port.out.AuthAppRepository;
import com.io.codetracker.domain.auth.entity.Auth;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

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
}
