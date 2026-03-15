package com.io.codetracker.application.user.service;

import com.io.codetracker.application.user.command.UserRegistrationCommand;
import com.io.codetracker.application.user.error.UserRegistrationError;
import com.io.codetracker.application.user.port.in.CompleteInitializationUseCase;
import com.io.codetracker.application.user.port.in.UserShallowRegistrationUseCase;
import com.io.codetracker.application.user.port.out.UserAuthPort;
import com.io.codetracker.application.user.port.out.CloudinaryPort;
import com.io.codetracker.application.user.port.out.UserAppRepository;
import com.io.codetracker.application.user.result.UserData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.domain.user.result.UserCreationResult;
import com.io.codetracker.domain.user.service.UserCreationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public final class UserRegistrationService implements UserShallowRegistrationUseCase, CompleteInitializationUseCase {

    private final UserAppRepository repository;
    private final UserCreationService userCreationService;
    private final UserAuthPort authRepository;
    private final CloudinaryPort cloudinaryPort;

    public String createShallowUser() {
        User user = userCreationService.createShallowUser();
        repository.save(user);
        return user.getUserId();
    }

    public Result<UserData, UserRegistrationError> completeInitialization(String userId, UserRegistrationCommand command) {
        Optional<User> userOpt = repository.findByUserId(userId);

        if (userOpt.isEmpty()) {
            return Result.fail(UserRegistrationError.USER_NOT_FOUND);
        }

        User user = userOpt.get();

        if (user.isHasFullyInitialized()) {
            return Result.fail(UserRegistrationError.USER_ALREADY_INITIALIZED);
        }

        String profileUrl = null;
        if (command.profile() != null) {
            try {
                profileUrl = cloudinaryPort.uploadProfilePicture(
                        command.profile().getBytes(),
                        userId
                );
            } catch (IOException e) {
                return Result.fail(UserRegistrationError.PROFILE_UPLOAD_FAILED);
            }
        }

        UserCreationResult result = userCreationService.finalizeUser(
                user,
                command.firstName(),
                command.lastName(),
                command.phoneNumber(),
                command.gender(),
                command.birthday(),
                profileUrl,
                command.bio()
        );

        if (result != UserCreationResult.SUCCESS) {

            if (profileUrl != null) {
                try {
                    cloudinaryPort.deleteImageByPublicId(user.getUserId());
                } catch (IOException e) {
                    return Result.fail(UserRegistrationError.PROFILE_DELETE_FAILED);
                }
            }

            return Result.fail(UserRegistrationError.INVALID_USER_DATA);
        }

        authRepository.markUserAsFullyInitialized(user.getUserId());
        repository.save(user);

        return Result.ok(UserData.from(user));
    }

}
