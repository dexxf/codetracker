package com.io.codetracker.adapter.user.in.rest;

import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.adapter.user.in.dto.request.UserProfileRequest;
import com.io.codetracker.adapter.user.in.dto.request.UserRegistrationRequest;
import com.io.codetracker.adapter.user.in.dto.response.*;
import com.io.codetracker.adapter.user.in.mapper.ProfilePictureHttpMapper;
import com.io.codetracker.adapter.user.in.mapper.UserProfileHttpMapper;
import com.io.codetracker.adapter.user.in.mapper.UserRegistrationHttpMapper;
import com.io.codetracker.application.user.command.UserProfileCommand;
import com.io.codetracker.application.user.command.UserRegistrationCommand;
import com.io.codetracker.application.user.error.UserProfileError;
import com.io.codetracker.application.user.error.UserRegistrationError;
import com.io.codetracker.application.user.port.in.*;
import com.io.codetracker.application.user.result.ProfilePictureResult;
import com.io.codetracker.application.user.result.UserData;
import com.io.codetracker.common.result.Result;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final CompleteInitializationUseCase completeInitializationUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final GetUserProfileDataUseCase getUserProfileDataUseCase;
    private final RemoveProfilePictureUseCase removeProfilePictureUseCase;
    private final UpdateProfilePictureUseCase updateProfilePictureUseCase;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserInitResponse> userRegistration(@AuthenticationPrincipal AuthPrincipal principal,
        @RequestPart("data") @Valid UserRegistrationRequest request,
        @RequestPart(value = "profile", required = false) MultipartFile profile) {

        UserRegistrationCommand command = new UserRegistrationCommand(request.firstName(), request.lastName(), 
        request.phoneNumber(),request.gender(),request.birthday(),profile,request.bio());

        Result<UserData, UserRegistrationError> result = completeInitializationUseCase.completeInitialization(principal.getUserId(),command);
        return result.success() ?
                ResponseEntity.ok(UserInitResponse.ok(result.data()))
                : ResponseEntity.status(UserRegistrationHttpMapper.toStatus(result.error()))
                .body(UserInitResponse.fail(UserRegistrationHttpMapper.toMessage(result.error())));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponse> updateUserProfile(
            @AuthenticationPrincipal AuthPrincipal principal,
            @Valid @RequestBody UserProfileRequest request) {
        UserProfileCommand command = new UserProfileCommand(request.firstName(), request.lastName(), request.gender(), request.phoneNumber(), request.bio(), request.birthday());
        Result<UserData, List<UserProfileError>> result = updateUserProfileUseCase.updateProfile(principal.getUserId(), command);
        return result.success() ?
                ResponseEntity.ok(UserProfileResponse.ok(result.data()))
                : ResponseEntity.status(UserProfileHttpMapper.toStatus(result.error()))
                        .body(UserProfileResponse.fail(UserProfileHttpMapper.toMessages(result.error())));
    } 
    
    @GetMapping("/profile")
    public ResponseEntity<UserData> getProfileData(@AuthenticationPrincipal AuthPrincipal authPrincipal) {
        Optional<UserData> dataOpt = getUserProfileDataUseCase.getProfileData(authPrincipal.getUserId());
        return dataOpt.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(dataOpt.get());
    }

    @DeleteMapping("/profile/remove")
    public ResponseEntity<String> removeProfilePicture(@AuthenticationPrincipal AuthPrincipal principal) {
        ProfilePictureResult res  = removeProfilePictureUseCase.removeProfilePicture(principal.getUserId());
          return ResponseEntity
                  .status(ProfilePictureHttpMapper.toStatus(res))
                  .body(ProfilePictureHttpMapper.toDeleteMessage(res));
    }

    @PatchMapping("/profile/update")
    public ResponseEntity<String> updateProfilePicture(@AuthenticationPrincipal AuthPrincipal principal, @RequestParam("file") MultipartFile multipartFile) {
        ProfilePictureResult res = updateProfilePictureUseCase.updateProfilePicture(principal.getUserId(), multipartFile);
        return ResponseEntity
                .status(ProfilePictureHttpMapper.toStatus(res))
                .body(ProfilePictureHttpMapper.toUpdateMessage(res));
    }

}