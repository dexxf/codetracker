package com.io.codetracker.adapter.auth.out.persistence.repository;

import com.io.codetracker.adapter.auth.out.persistence.mapper.AuthAccountAggregateMapper;
import com.io.codetracker.adapter.auth.out.persistence.mapper.AuthMapper;
import com.io.codetracker.adapter.auth.out.persistence.mapper.GithubAccountMapper;
import com.io.codetracker.application.auth.port.out.AuthAppRepository;
import com.io.codetracker.application.user.port.out.UserAuthPort;
import com.io.codetracker.domain.auth.aggregate.AuthAccountAggregate;
import com.io.codetracker.domain.auth.entity.Auth;
import com.io.codetracker.domain.auth.valueobject.Status;
import com.io.codetracker.infrastructure.auth.persistence.entity.AuthEntity;
import com.io.codetracker.infrastructure.auth.persistence.repository.JpaAuthRepository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AuthAppRepositoryImpl implements AuthAppRepository,UserAuthPort {

    private final JpaAuthRepository jpa;

    public AuthAppRepositoryImpl(
            @Qualifier("jpaAuthRepository") JpaAuthRepository jpa
    ) {
        this.jpa = jpa;
    }

    @Override
    public void save(AuthAccountAggregate aggregate) {
        AuthEntity authEntity = jpa.save(AuthAccountAggregateMapper.toEntity(aggregate));
        authEntity.linkGithubAccount(GithubAccountMapper.toEntity(aggregate.githubAccount()));
        jpa.save(authEntity);
    }

    @Override
    public boolean emailExists(String email) {
        return jpa.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpa.existsByUsername(username);
    }

    @Override
    public Optional<Auth> findByAuthId(String authId) {
        return jpa.findById(authId).map(AuthMapper::toDomain);
    }

    @Override
    public boolean existsByAuthId(String authId) {
        return jpa.existsById(authId);
    }

    @Override
    public Optional<AuthAccountAggregate> findByGithubId(Long githubId) {
        return jpa.findByGithubAccountEntity_GithubId(githubId)
                .map(entity -> new AuthAccountAggregate(
                        AuthMapper.toDomain(entity),
                        GithubAccountMapper.toDomain(entity.getGithubAccountEntity())
                ));
    }

    @Override
    public void markUserAsFullyInitialized(String userId) {
        Optional<AuthEntity> authEntityOpt = jpa.findByUserId(userId);
        if (authEntityOpt.isPresent()) {
            Auth auth = AuthMapper.toDomain(authEntityOpt.get());
            auth.changeStatus(Status.ACTIVE);
            jpa.save(AuthMapper.toEntity(auth));
        }
    }

    @Override
    public Optional<String> getUserIdByAuthId(String authId) {
        Optional<AuthEntity> authEntityOpt = jpa.findById(authId);
        if(authEntityOpt.isEmpty()) return Optional.empty();
        return Optional.of(authEntityOpt.get().getUserId());
    }
}
