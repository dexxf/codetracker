package com.io.codetracker.adapter.user.out.persistence.mapper;

import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.domain.user.valueobject.Birthday;
import com.io.codetracker.domain.user.valueobject.Gender;
import com.io.codetracker.domain.user.valueobject.PhoneNumber;
import com.io.codetracker.infrastructure.user.persistence.entity.UserEntity;

public final class UserMapper {

    public static User toDomain(UserEntity entity) {
        return new User(
                entity.getUserId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getGender() != null ? Gender.valueOf(entity.getGender()) : null,
                entity.getPhoneNumber() != null ? PhoneNumber.of(entity.getPhoneNumber()).data() : null,
                entity.getProfileUrl(),
                entity.getBio(),
                entity.getBirthday() != null ? Birthday.of(entity.getBirthday()).data() : null,
                entity.isHasFullyInitialized(),
                entity.getCreatedAt() != null ? entity.getCreatedAt() : null
        );
    }

    public static UserEntity toEntity(User user) {
        return new UserEntity(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber() != null ? user.getPhoneNumber().getValue() : null,
                user.getGender() != null ? user.getGender().name() : null,
                user.getBirthday() != null ? user.getBirthday().getValue() : null,
                user.getProfileUrl(),
                user.getBio() != null ? user.getBio() : null,
                user.getCreatedAt(),
                user.isHasFullyInitialized()
        );
    }
}
