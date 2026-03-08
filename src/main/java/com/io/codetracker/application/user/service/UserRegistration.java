package com.io.codetracker.application.user.service;

import com.io.codetracker.application.user.command.UserRegistrationCommand;
import com.io.codetracker.application.user.port.in.response.UserRegistrationResponseDTO;
import com.io.codetracker.application.user.port.out.UserAuthPort;
import com.io.codetracker.application.user.port.out.CloudinaryPort;
import com.io.codetracker.application.user.port.out.UserAppRepository;
import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.domain.user.result.UserCreationResult;
import com.io.codetracker.domain.user.service.UserCreationService;
import com.io.codetracker.domain.user.valueobject.Gender;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public final class UserRegistration {

    private final UserAppRepository repository;
    private final UserCreationService userCreationService;
    private final UserAuthPort authRepository;
    private final CloudinaryPort cloudinaryPort;

    public UserRegistration(UserAppRepository repository, UserCreationService userCreationService, UserAuthPort authRepository, CloudinaryPort cloudinaryPort) {
        this.repository = repository;
        this.userCreationService = userCreationService;
        this.authRepository = authRepository;
        this.cloudinaryPort = cloudinaryPort;
    }

    public String createShallowUser() {
        User user = userCreationService.createShallowUser();
        repository.save(user);
        return user.getUserId();
    }

    public UserRegistrationResponseDTO completeInitialization(String userId, UserRegistrationCommand command) {
        if (userId.isEmpty()) return UserRegistrationResponseDTO.fail("User not found for the given auth ID.");
        Optional<User> userOpt = repository.findByUserId(userId);

        if (userOpt.isEmpty()) return UserRegistrationResponseDTO.fail("User not found.");

        User user = userOpt.get();

        if(user.isHasFullyInitialized()) return UserRegistrationResponseDTO.fail("User already fully initialized.");

        Gender gender;
        try {
            gender = Gender.valueOf(command.gender().toUpperCase());
        } catch (IllegalArgumentException e) {
            return UserRegistrationResponseDTO.fail("Invalid gender.");
        }

        String profileUrl;
        try {
             // Use UserId as their publicId for their Profile Picture
            profileUrl = cloudinaryPort.uploadProfilePicture(command.profile().getBytes(), userId);
        } catch (IOException e) {
            return UserRegistrationResponseDTO.fail("Cant upload profile.");
        }

        UserCreationResult userFinalizeResult = userCreationService.finalizeUser(
                user,
                command.firstName(),
                command.lastName(),
                command.phoneNumber(),
                gender,
                command.birthday(),
                profileUrl,
                command.bio());

        if (userFinalizeResult != UserCreationResult.SUCCESS) {
            try {
                cloudinaryPort.deleteImageByPublicId(profileUrl);
            } catch (IOException e) {
                return UserRegistrationResponseDTO.fail("Cant upload image file.");
            }
            return UserRegistrationResponseDTO.fail(userFinalizeResult.getMessage());
        }

        authRepository.markUserAsFullyInitialized(user.getUserId());
        repository.save(user);

        Map<String, Object> data = new HashMap<>();
        data.put("userId", user.getUserId());
        data.put("firstName", user.getFirstName());
        data.put("lastName", user.getLastName());
        data.put("gender", user.getGender().name());
        data.put("phoneNumber", user.getPhoneNumber().getValue());
        data.put("birthday", user.getBirthday().getValue());
        data.put("bio", user.getBio());
        data.put("profileUrl", user.getProfileUrl());
        data.put("hasFullyInitialized", user.isHasFullyInitialized());
        data.put("createdAt", user.getCreatedAt());

        return UserRegistrationResponseDTO.ok(data, "Profile completed.");
    }

}
