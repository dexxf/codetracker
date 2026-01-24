package com.io.codetracker.adapter.user.out.persistence.repository;

import com.io.codetracker.adapter.user.out.persistence.mapper.UserMapper;
import com.io.codetracker.application.user.port.out.UserAppRepository;
import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.infrastructure.user.persistence.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserAppRepositoryImpl implements UserAppRepository {

    private final JpaUserRepository jpa;

    public UserAppRepositoryImpl (JpaUserRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void save(User user) {
        jpa.save(UserMapper.toEntity(user));
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        Optional<UserEntity> userEntityOpt= jpa.findById(userId);

        if(userEntityOpt.isEmpty()) return Optional.empty();
        UserEntity user = userEntityOpt.get();
        return Optional.of(UserMapper.toDomain(user));
    }
}