package com.io.codetracker.application.auth.port.out;

import com.io.codetracker.domain.auth.entity.Auth;

public interface AuthAppRepository {
    void save(Auth auth);
    boolean emailExists(String email);
}
