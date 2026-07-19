package com.io.codetracker.domain.user.service;

import com.io.codetracker.domain.user.exception.UserNotFoundException;
import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.domain.user.result.UserProfileUpdateResult;
import com.io.codetracker.domain.user.valueobject.Gender;

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
     * @return List of failed validation rules, empty if update was successful
     * @throws UserNotFoundException if user is null
     */

    public List<UserProfileUpdateResult> update(User user, String firstName, String lastName, String gender) {
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

        return errors;
    }
}
