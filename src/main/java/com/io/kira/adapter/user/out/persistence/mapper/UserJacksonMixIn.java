package com.io.kira.adapter.user.out.persistence.mapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.io.kira.domain.user.entity.User;
import com.io.kira.domain.user.valueobject.Gender;

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
        throw new UnsupportedOperationException();
    }
}
