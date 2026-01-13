package com.io.codetracker.adapter.auth.out.persistence.repository;

import com.io.codetracker.domain.auth.repository.AuthDomainRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class DomainAuthRepositoryImpl implements AuthDomainRepository {

    private final JpaAuthRepository jpa;

    public DomainAuthRepositoryImpl(@Qualifier("jpaAuthRepository") JpaAuthRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public boolean existsByUsername(String username) {
        var authOpt = jpa.findByUsername(username);
        return authOpt.isPresent();
    }

    @Override
    public boolean existsById(String authId) {
        return jpa.existsById(authId);
    }

}