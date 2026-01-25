package com.io.codetracker.application.user.service;

import com.io.codetracker.application.user.command.UserProfileCommand;
import com.io.codetracker.application.user.response.UserProfileResponseDTO;
import com.io.codetracker.application.user.port.out.UserAppRepository;
import com.io.codetracker.domain.user.entity.User;
import com.io.codetracker.domain.user.result.UserProfileUpdaterResult;
import com.io.codetracker.domain.user.service.UserProfileUpdater;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class UserProfileService {
       private final UserAppRepository repository;
       private final UserProfileUpdater userProfileUpdater;

       public UserProfileService (UserProfileUpdater userProfileUpdater,UserAppRepository repository) {
           this.repository = repository;
           this.userProfileUpdater = userProfileUpdater;
       }

       public UserProfileResponseDTO execute(UserProfileCommand command) {
           Optional<User> userOpt = repository.findByUserId(command.userId());

           if(userOpt.isEmpty())
               return UserProfileResponseDTO.fail("User not found.");

           User user = userOpt.get();

           UserProfileUpdaterResult userProfileUpdaterResult = userProfileUpdater.update(user, command.firstName(), command.lastName(),
                   command.gender(), command.phoneNumber(), command.bio(), command.birthday());

           if(userProfileUpdaterResult.hasErrors())
               return UserProfileResponseDTO.fail(userProfileUpdaterResult.errors());

           repository.save(user);
           return UserProfileResponseDTO.ok(user);
       }
}
