package com.io.codetracker.domain.auth.valueobject;

import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.auth.result.PasswordResult;

public final class HashedPassword {

    private final String value;

    private HashedPassword (String value) {
        this.value = value;
    }

    public static Result<HashedPassword, PasswordResult> of(String value) {
        if (value == null || value.isEmpty()) {
            return Result.fail(PasswordResult.HASHED_PASSWORD_EMPTY);
        }

        if (value.length() != 60) {
            return Result.fail(PasswordResult.INVALID_HASHED_PASSWORD_LENGTH);
        }

        if (!value.startsWith("$2a$") && !value.startsWith("$2b$") && !value.startsWith("$2y$")) {
            return Result.fail(PasswordResult.INVALID_HASHED_FORMAT);
        }

        return Result.ok(new HashedPassword(value));
    }

    public String getValue() {
        return value;
    }
}
