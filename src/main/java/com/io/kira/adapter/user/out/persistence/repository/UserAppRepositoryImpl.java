package com.io.kira.adapter.user.out.persistence.repository;


import java.util.UUID;

import com.io.kira.adapter.user.out.cache.UserCacheNames;
import com.io.kira.adapter.user.out.persistence.mapper.UserMapper;
import com.io.kira.application.user.port.out.UserAppRepository;
import com.io.kira.domain.classroom.repository.ClassroomUserDomainPort;
import com.io.kira.domain.user.entity.User;
import com.io.kira.infrastructure.user.persistence.repository.JpaUserRepository;
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
    @CacheEvict(value = UserCacheNames.USER_PROFILE, key = "#user.userId")
    public void save(User user) {
        jpa.save(UserMapper.toEntity(user));
    }

    @Override
    @Cacheable(
            value = UserCacheNames.USER_PROFILE,
            key = "#userId"
    )
    public Optional<User> findByUserId(UUID userId) {
        return jpa.findById(userId)
                .map(UserMapper::toDomain);
    }


    @Override
    @CacheEvict(value = UserCacheNames.USER_PROFILE, key = "#userId")
    public int updateProfileUrlByUserId(UUID userId, String newProfileUrl) {
        return jpa.updateProfileUrlByUserId(userId,newProfileUrl);
    }

    @Override
    public boolean existsByUserId(UUID instructorUserId) {
        return jpa.existsById(instructorUserId);
    }
}