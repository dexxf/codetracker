package com.io.codetracker.domain.user.entity;

import com.io.codetracker.domain.user.valueobject.Birthday;
import com.io.codetracker.domain.user.valueobject.Gender;
import com.io.codetracker.domain.user.valueobject.PhoneNumber;

import java.time.LocalDateTime;

public final class User {

    private final String userId;
    private String firstName;
    private String lastName;
    private Gender gender;
    private PhoneNumber phoneNumber;
    private String profileUrl;
    private String bio;
    private Birthday birthday;

    private boolean hasFullyInitialized;
    private final LocalDateTime createdAt;

    public User(String userId, LocalDateTime createdAt, boolean hasFullyInitialized) {
        this.userId = userId;
        this.createdAt = createdAt;
        this.hasFullyInitialized = hasFullyInitialized;
    }

    public User(String userId, String firstName, String lastName, Gender gender, PhoneNumber phoneNumber,
                String profileUrl, String bio, Birthday birthday, boolean hasFullyInitialized, LocalDateTime createdAt) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.profileUrl = profileUrl;
        this.bio = bio;
        this.birthday = birthday;
        this.hasFullyInitialized = hasFullyInitialized;
        this.createdAt = createdAt;
    }

    public String getUserId() {
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

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    public void setBirthday(Birthday birthday) {
        this.birthday = birthday;
    }

    public boolean isHasFullyInitialized() {
        return hasFullyInitialized;
    }

    public void setHasFullyInitialized(boolean hasFullyInitialized) {
        this.hasFullyInitialized = hasFullyInitialized;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
