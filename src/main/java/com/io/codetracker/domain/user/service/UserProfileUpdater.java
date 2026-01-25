package com.io.codetracker.domain.user.service;

import com.io.codetracker.domain.user.exception.UserNotFoundException;
import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.domain.user.result.UserProfileUpdaterResult;
import com.io.codetracker.domain.user.valueobject.Birthday;
import com.io.codetracker.domain.user.valueobject.Gender;
import com.io.codetracker.domain.user.valueobject.PhoneNumber;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Updates the attributes of an existing User.
 */
public final class UserProfileUpdater {

    /**
     * @param user        the User to update; cannot be null
     * @param firstName   new first name
     * @param lastName    new last name
     * @param gender      new gender value
     * @param phoneNumber new phone number
     * @param bio         new bio
     * @param birthday    new birthday
     * @return result indicating success or validation errors
     * @throws UserNotFoundException if user is null
     */

    public UserProfileUpdaterResult update(User user, String firstName, String lastName,
                                           String gender, String phoneNumber,
                                           String bio, LocalDate birthday) {
        if (user == null) throw new UserNotFoundException("User not found.");

        Map<String, String> errors = new HashMap<>();

        if (lastName != null) {
            String trimmedLastName = lastName.trim();
            if (trimmedLastName.isBlank() || trimmedLastName.length() < 2) {
                errors.put("lastName", "Last name must be at least 2 characters.");
            } else {
                user.setLastName(trimmedLastName);
            }
        }

        if (firstName != null) {
            String trimmedFirstName = firstName.trim();
            if (trimmedFirstName.isBlank() || trimmedFirstName.length() < 2) {
                errors.put("firstName", "First name must be at least 2 characters.");
            } else {
                user.setFirstName(trimmedFirstName);
            }
        }

        if (gender != null) {
            try {
                user.setGender(Gender.valueOf(gender.trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
                errors.put("gender", "Invalid gender value.");
            }
        }

        if (phoneNumber != null) {
            var phoneResult = PhoneNumber.of(phoneNumber.trim());
            if (!phoneResult.success()) errors.put("phoneNumber", phoneResult.error().message());
            else user.setPhoneNumber(phoneResult.data());
        }

        if (bio != null) {
            if (bio.length() > 350) errors.put("bio", "Bio must be 350 characters or less.");
            else user.setBio(bio);
        }

        if (birthday != null) {
            var birthdayResult = Birthday.of(birthday);
            if (!birthdayResult.success()) errors.put("birthday", birthdayResult.error().message());
            else user.setBirthday(birthdayResult.data());
        }

        return errors.isEmpty() ? UserProfileUpdaterResult.ok() : UserProfileUpdaterResult.fail(errors);
    }
}
