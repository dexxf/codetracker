package com.io.codetracker.infrastructure.user.persistence.repository;

import com.io.codetracker.infrastructure.user.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface JpaUserRepository extends JpaRepository<UserEntity, String> {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE UserEntity u SET u.profileUrl = :profileUrl WHERE u.userId = :userId")
    int updateProfileUrlByUserId(String userId, String profileUrl);
}
