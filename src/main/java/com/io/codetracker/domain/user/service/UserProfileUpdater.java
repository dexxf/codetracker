package com.io.codetracker.domain.user.service;

import com.io.codetracker.domain.user.exception.UserNotFoundException;
import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.domain.user.result.UserProfileUpdateResult;
import com.io.codetracker.domain.user.valueobject.Birthday;
import com.io.codetracker.domain.user.valueobject.Gender;
import com.io.codetracker.domain.user.valueobject.PhoneNumber;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
     * @return List of failed validation rules, empty if update was successful
     * @throws UserNotFoundException if user is null
     */

    public List<UserProfileUpdateResult> update(User user, String firstName, String lastName, String gender, String phoneNumber, String bio, LocalDate birthday) {
        if (user == null) throw new UserNotFoundException("User not found.");

        List<UserProfileUpdateResult> errors = new ArrayList<>();

        if (lastName != null) {
            String trimmed = lastName.trim();
            if (trimmed.isBlank() || trimmed.length() < 2) {
                errors.add(UserProfileUpdateResult.INVALID_LAST_NAME);
            } else {
                user.setLastName(trimmed);
            }
        }

        if (firstName != null) {
            String trimmed = firstName.trim();
            if (trimmed.isBlank() || trimmed.length() < 2) {
                errors.add(UserProfileUpdateResult.INVALID_FIRST_NAME);
            } else {
                user.setFirstName(trimmed);
            }
        }

        if (gender != null) {
            try {
                user.setGender(Gender.valueOf(gender.trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
                errors.add(UserProfileUpdateResult.INVALID_GENDER);
            }
        }

        if (phoneNumber != null) {
            var phoneResult = PhoneNumber.of(phoneNumber.trim());
            if (!phoneResult.success()) errors.add(UserProfileUpdateResult.INVALID_PHONE_NUMBER);
            else user.setPhoneNumber(phoneResult.data());
        }

        if (bio != null) {
            if (bio.length() > 350) errors.add(UserProfileUpdateResult.INVALID_BIO);
            else user.setBio(bio);
        }

        if (birthday != null) {
            var birthdayResult = Birthday.of(birthday);
            if (!birthdayResult.success()) errors.add(UserProfileUpdateResult.INVALID_BIRTHDAY);
            else user.setBirthday(birthdayResult.data());
        }

        return errors;
    }
}
