package com.io.codetracker.application.user.port.out;

import com.io.codetracker.domain.user.entity.User;

import java.util.Optional;

public interface UserAppRepository {
    void save(User user);
    Optional<User> findByUserId(String userId);
}
