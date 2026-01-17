package com.io.codetracker.infrastructure.user.factory;

import com.io.codetracker.common.id.IDGenerator;
import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.domain.user.factory.UserFactory;
import com.io.codetracker.domain.user.valueobject.Birthday;
import com.io.codetracker.domain.user.valueobject.Gender;
import com.io.codetracker.domain.user.valueobject.PhoneNumber;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public final class DefaultUserFactory implements UserFactory {

    private final IDGenerator idGenerator;

    public DefaultUserFactory(@Qualifier("userIDGenerator") IDGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public User createPartial() {
        return new User(idGenerator.generate(), LocalDateTime.now(), false);
    }

    @Override
    public void initialize(User user, String firstName, String lastName, Gender gender, PhoneNumber phoneNumber, Birthday birthday, String profileUrl, String bio) {
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhoneNumber(phoneNumber);
        user.setGender(gender);
        user.setBirthday(birthday);
        user.setProfileUrl(profileUrl);
        user.setHasFullyInitialized(true);
        user.setBio(bio);
    }
}
