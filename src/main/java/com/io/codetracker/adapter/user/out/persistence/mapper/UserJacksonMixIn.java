package com.io.codetracker.adapter.user.out.persistence.mapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.domain.user.valueobject.Gender;

import java.util.UUID;

public abstract class UserJacksonMixIn {

    @JsonCreator
    static User reconstitute(
            UUID userId,
            String firstName,
            String lastName,
            Gender gender,
            String profileUrl,
            boolean hasFullyInitialized) {
        return User.reconstitute(userId, firstName, lastName, gender, profileUrl, hasFullyInitialized);
    }
}
