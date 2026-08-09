package com.io.kira.domain.auth.service;

import com.io.kira.common.result.Result;
import com.io.kira.domain.auth.result.PasswordResult;
import com.io.kira.domain.auth.valueobject.HashedPassword;

public class PasswordService {

    private final PasswordHasher hasher;

    public PasswordService (PasswordHasher hasher) {
        this.hasher = hasher;
    }

    public Result<HashedPassword, PasswordResult> validatePassword(String rawPassword) {

        if(rawPassword.length() < 8) return Result.fail(PasswordResult.INVALID_RAW_PASSWORD_LENGTH);

        String hashed = hasher.encode(rawPassword);
        var hashResult = HashedPassword.of(hashed);

        if(!hashResult.success()) return Result.fail(hashResult.error());

        return Result.ok(hashResult.data());
    }
}
