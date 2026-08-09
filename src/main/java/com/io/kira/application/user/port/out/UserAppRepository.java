package com.io.kira.application.user.port.out;

import com.io.kira.domain.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserAppRepository {
    void save(User user);
    Optional<User> findByUserId(UUID userId);
    int updateProfileUrlByUserId(UUID userId, String newProfileUrl);
}

