package com.io.codetracker.application.user.port.out;

import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.domain.user.exception.UserNotFoundException;

import java.util.UUID;

public interface UserAppRepository {
    void save(User user);
    User findByUserId(UUID userId) throws UserNotFoundException;
    int updateProfileUrlByUserId(UUID userId, String newProfileUrl);
}

