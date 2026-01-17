package com.io.codetracker.domain.user.service;

import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.domain.user.factory.UserFactory;
import com.io.codetracker.domain.user.result.UserCreationResult;
import com.io.codetracker.domain.user.valueobject.Birthday;
import com.io.codetracker.domain.user.valueobject.Gender;
import com.io.codetracker.domain.user.valueobject.PhoneNumber;

import java.time.LocalDate;

public final class UserCreationService {

    private final UserFactory factory;

    public UserCreationService (UserFactory factory) {
        this.factory = factory;
    }

    public User createShallowUser() {
        return factory.createPartial();
    }

    public UserCreationResult finalizeUser(User user, String firstName, String lastName, String phoneNumber, Gender gender, LocalDate birthday, String profileUrl, String bio) {
        if(user == null) return UserCreationResult.USER_NULL;

        if(firstName == null || firstName.isEmpty() || lastName == null || lastName.isEmpty()) return UserCreationResult.NAME_REQUIRED;

        var phoneNumberResult = PhoneNumber.of(phoneNumber);

        if(!phoneNumberResult.success()) return switch (phoneNumberResult.error()) {
            case PHONE_NUMBER_EMPTY -> UserCreationResult.PHONE_NUMBER_EMPTY;
            case INVALID_PHONE_NUMBER_FORMAT -> UserCreationResult.INVALID_PHONE_NUMBER_FORMAT;
        };

        var birthdayResult = Birthday.of(birthday);

        if (!birthdayResult.success()) return switch (birthdayResult.error()) {
            case BIRTHDAY_IN_FUTURE -> UserCreationResult.BIRTHDAY_IN_FUTURE;
            case TOO_YOUNG -> UserCreationResult.TOO_YOUNG;
            case TOO_OLD -> UserCreationResult.TOO_OLD;
        };

        if(gender == null) {
            return UserCreationResult.INVALID_GENDER;
        }

        factory.initialize(user, firstName, lastName, gender, phoneNumberResult.data(), birthdayResult.data(), profileUrl, bio);
        return UserCreationResult.SUCCESS;
    }

}
