package com.io.codetracker.domain.user.factory;

import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.domain.user.valueobject.Birthday;
import com.io.codetracker.domain.user.valueobject.Gender;
import com.io.codetracker.domain.user.valueobject.PhoneNumber;


public interface UserFactory {
    User createPartial();
    void initialize(User user,String firstName,String lastName,Gender gender,PhoneNumber phoneNumber,Birthday birthday,String profileUrl,String bio);
}
