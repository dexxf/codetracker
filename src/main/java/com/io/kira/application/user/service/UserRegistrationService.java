package com.io.kira.application.user.service;

import com.io.kira.application.user.command.UserRegistrationCommand;
import com.io.kira.application.user.error.UserRegistrationError;
import com.io.kira.application.user.port.in.CompleteInitializationUseCase;
import com.io.kira.application.user.port.in.UserShallowRegistrationUseCase;
import com.io.kira.application.user.port.out.UserAuthPort;
import com.io.kira.application.user.port.out.CloudinaryPort;
import com.io.kira.application.user.port.out.UserAppRepository;
import com.io.kira.application.user.result.UserData;
import com.io.kira.common.result.Result;
import com.io.kira.domain.user.entity.User;
import com.io.kira.domain.user.valueobject.Gender;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public final class UserRegistrationService implements UserShallowRegistrationUseCase, CompleteInitializationUseCase {

    private final UserAppRepository repository;
    private final UserAuthPort authRepository;
    private final CloudinaryPort cloudinaryPort;

    public UUID createShallowUser() {
        User user = User.createShallow(UUID.randomUUID());
        repository.save(user);
        return user.getUserId();
    }

    @Override
    public Result<UserData, UserRegistrationError> completeInitialization(UUID userId, UserRegistrationCommand command) {
        Optional<User> userOpt = repository.findByUserId(userId);

        if (userOpt.isEmpty()) {
            return Result.fail(UserRegistrationError.USER_NOT_FOUND);
        }

        User user = userOpt.get();

        if (user.getHasFullyInitialized()) {
            return Result.fail(UserRegistrationError.USER_ALREADY_INITIALIZED);
        }

        String profileUrl = null;
        if (command.profile() != null) {
            try {
                profileUrl = cloudinaryPort.uploadProfilePicture(
                        command.profile().getBytes(),
                        userId.toString()
                );
            } catch (IOException e) {
                return Result.fail(UserRegistrationError.PROFILE_UPLOAD_FAILED);
            }
        }

        Gender gender;

        try {
            gender = Gender.valueOf(command.gender());
        } catch(IllegalArgumentException e) {
            return Result.fail(UserRegistrationError.INVALID_GENDER);
        }

        User result = User.createFullyInitialized(
                user.getUserId(),
                command.firstName(),
                command.lastName(),
                gender,
                profileUrl
        );

            if (profileUrl != null) {
                try {
                    cloudinaryPort.deleteImageByPublicId(user.getUserId().toString());
                } catch (IOException e) {
                    return Result.fail(UserRegistrationError.PROFILE_DELETE_FAILED);
                }
            }

        authRepository.changeStatusActiveByUserId(user.getUserId());
        repository.save(result);

        return Result.ok(UserData.from(result));
    }

}

