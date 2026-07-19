package com.io.codetracker.application.user.port.out;

import com.io.codetracker.domain.user.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserAppRepository {
    void save(User user);
    Optional<User> findByUserId(UUID userId);
    int updateProfileUrlByUserId(UUID userId, String newProfileUrl);
}

