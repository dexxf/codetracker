package com.io.codetracker.application.user.service;

import com.io.codetracker.application.user.command.UserProfileCommand;
import com.io.codetracker.application.user.error.UserProfileError;
import com.io.codetracker.application.user.port.in.GetUserProfileDataUseCase;
import com.io.codetracker.application.user.port.in.UpdateUserProfileUseCase;
import com.io.codetracker.application.user.port.out.UserAppRepository;
import com.io.codetracker.application.user.result.UserData;
import com.io.codetracker.common.result.Result;
import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.domain.user.result.UserProfileUpdateResult;
import com.io.codetracker.domain.user.service.UserProfileUpdater;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public final class UserProfileService implements UpdateUserProfileUseCase, GetUserProfileDataUseCase {

       private final UserAppRepository repository;
       private final UserProfileUpdater userProfileUpdater;

       public Result<UserData, List<UserProfileError>> updateProfile(String userId, UserProfileCommand command) {
           Optional<User> userOpt = repository.findByUserId(userId);

           if(userOpt.isEmpty())
               return Result.fail(List.of(UserProfileError.USER_NOT_FOUND));

           User user = userOpt.get();

           List<UserProfileUpdateResult> userProfileUpdaterResult = userProfileUpdater.update(user, command.firstName(), command.lastName(),
                   command.gender(), command.phoneNumber(), command.bio(), command.birthday());

           if(!userProfileUpdaterResult.isEmpty())
               return Result.fail(UserProfileError.from(userProfileUpdaterResult));

           repository.save(user);
           return Result.ok(UserData.from(user));
       }

       public Optional<UserData> getProfileData(String userId) {
            Optional<User> userOpt = repository.findByUserId(userId);
           return userOpt.map(UserData::from);
       }
}
