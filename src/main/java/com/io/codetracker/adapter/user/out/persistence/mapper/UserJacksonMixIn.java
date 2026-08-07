package com.io.codetracker.adapter.user.out.persistence.mapper;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.domain.user.valueobject.Gender;

import java.util.UUID;

public abstract class UserJacksonMixIn {

    @JsonCreator
    static User reconstitute(
            @JsonProperty("userId") UUID userId,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("gender") Gender gender,
            @JsonProperty("profileUrl") String profileUrl,
            @JsonProperty("hasFullyInitialized") boolean hasFullyInitialized) {
        return User.reconstitute(userId, firstName, lastName, gender, profileUrl, hasFullyInitialized);
    }
}
