package com.io.codetracker.adapter.user.out.persistence.repository;


import java.util.UUID;
import com.io.codetracker.adapter.user.out.persistence.mapper.UserMapper;
import com.io.codetracker.application.user.port.out.UserAppRepository;
import com.io.codetracker.common.cache.CacheNames;
import com.io.codetracker.domain.classroom.repository.ClassroomUserDomainPort;
import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.domain.user.exception.UserNotFoundException;
import com.io.codetracker.infrastructure.user.persistence.entity.UserEntity;
import com.io.codetracker.infrastructure.user.persistence.repository.JpaUserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserAppRepositoryImpl implements UserAppRepository,ClassroomUserDomainPort {

    private final JpaUserRepository jpa;

    public UserAppRepositoryImpl (JpaUserRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    @CacheEvict(value = CacheNames.USER_PROFILE, key = "#user.userId")
    public void save(User user) {
        jpa.save(UserMapper.toEntity(user));
    }

    @Override
    @Cacheable(
            value = CacheNames.USER_PROFILE,
            key = "#userId"
    )
    public User findByUserId(UUID userId) throws UserNotFoundException {
        Optional<UserEntity> userEntityOpt = jpa.findById(userId);

        if (userEntityOpt.isEmpty()) {
            throw new UserNotFoundException(userId.toString());
        }

        UserEntity user = userEntityOpt.get();
        return UserMapper.toDomain(user);
    }


    @Override
    @CacheEvict(value = CacheNames.USER_PROFILE, key = "#userId")
    public int updateProfileUrlByUserId(UUID userId, String newProfileUrl) {
        return jpa.updateProfileUrlByUserId(userId,newProfileUrl);
    }

    @Override
    public boolean existsByUserId(UUID instructorUserId) {
        return jpa.existsById(instructorUserId);
    }
}