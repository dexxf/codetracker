package com.io.codetracker.domain.user.entity;

import com.io.codetracker.domain.user.valueobject.Gender;

import java.util.Objects;
import java.util.UUID;


public final class User {

    private final UUID userId;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String profileUrl;

    private final Boolean hasFullyInitialized;

    private User(UUID userId, Boolean hasFullyInitialized) {
        this.userId = userId;
        this.hasFullyInitialized = hasFullyInitialized;
    }

    private User(UUID userId, String firstName, String lastName, Gender gender,
                 String profileUrl, Boolean hasFullyInitialized) {
        this.userId = Objects.requireNonNull(userId, "userId must not be null");
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.profileUrl = profileUrl;
        this.hasFullyInitialized = Objects.requireNonNull(hasFullyInitialized, "hasFullyInitialized must not be null");;
    }

    public static User createShallow(UUID userId) {
        return new User(userId, false);
    }

    public static User createFullyInitialized(UUID userId, String firstName, String lastName, Gender gender, String profileUrl) {

        Objects.requireNonNull(firstName, "firstName must not be null");
        Objects.requireNonNull(lastName, "lastName must not be null");
        Objects.requireNonNull(gender, "gender must not be null");

        return new User(userId, firstName, lastName, gender, profileUrl, true);
    }

    public static User reconstitute(UUID userId, String firstName, String lastName, Gender gender
                                    , String profileUrl,
                                    boolean hasFullyInitialized) {
        return new User(userId, firstName, lastName, gender,  profileUrl,
                hasFullyInitialized);
    }

    public UUID getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }


    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public Boolean getHasFullyInitialized() {
        return hasFullyInitialized;
    }
}
