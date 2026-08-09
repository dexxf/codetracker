package com.io.kira.application.user.service;


import java.util.UUID;
import com.io.kira.application.user.command.UserProfileCommand;
import com.io.kira.application.user.error.UserProfileError;
import com.io.kira.application.user.port.in.GetUserProfileDataUseCase;
import com.io.kira.application.user.port.in.UpdateUserProfileUseCase;
import com.io.kira.application.user.port.out.UserAppRepository;
import com.io.kira.application.user.result.UserData;
import com.io.kira.common.result.Result;
import com.io.kira.domain.user.entity.User;
import com.io.kira.domain.user.result.UserProfileUpdateResult;
import com.io.kira.domain.user.service.UserProfileUpdater;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public final class UserProfileService implements UpdateUserProfileUseCase, GetUserProfileDataUseCase {

       private final UserAppRepository repository;
       private final UserProfileUpdater userProfileUpdater;

       @Override
       public Result<UserData, List<UserProfileError>> updateProfile(UUID userId, UserProfileCommand command) {
           Optional<User> userOpt = repository.findByUserId(userId);

           if (userOpt.isEmpty()) {
               return Result.fail(List.of(UserProfileError.USER_NOT_FOUND));
           }

           User user = userOpt.get();

           List<UserProfileUpdateResult> userProfileUpdaterResult = userProfileUpdater.update(user, command.firstName(), command.lastName(),
                   command.gender());

           if(!userProfileUpdaterResult.isEmpty())
               return Result.fail(UserProfileError.from(userProfileUpdaterResult));

           repository.save(user);
           return Result.ok(UserData.from(user));
       }

    @Override
    public Optional<UserData> getProfileData(UUID userId) {
        return repository.findByUserId(userId)
                .map(UserData::from);
    }
}

