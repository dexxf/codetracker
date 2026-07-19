package com.io.codetracker.adapter.user.out.persistence.mapper;

import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.infrastructure.user.persistence.entity.UserEntity;

public final class UserMapper {

    private UserMapper() {
    }

    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        return User.reconstitute(
                entity.getUserId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getGender(),
                entity.getProfileUrl(),
                entity.getHasFullyInitialized()
        );
    }

    public static UserEntity toEntity(User user) {
        if (user == null) {
            return null;
        }

        return UserEntity.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .profileUrl(user.getProfileUrl())
                .hasFullyInitialized(user.getHasFullyInitialized())
                .build();
    }
}