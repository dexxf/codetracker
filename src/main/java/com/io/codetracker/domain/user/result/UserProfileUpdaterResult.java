package com.io.codetracker.domain.user.result;

import java.util.Collections;
import java.util.Map;

public record UserProfileUpdaterResult(boolean success, Map<String, Object> errors) {
    public static UserProfileUpdaterResult ok() {
        return new UserProfileUpdaterResult(true, Collections.emptyMap());
    }

    public static UserProfileUpdaterResult fail(Map<String, String> errors) {
        return new UserProfileUpdaterResult(false, Map.copyOf(errors));
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
