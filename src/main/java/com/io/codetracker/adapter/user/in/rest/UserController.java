package com.io.codetracker.adapter.user.in.rest;

import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.adapter.user.in.dto.UserProfileRequest;
import com.io.codetracker.adapter.user.in.dto.UserRegistrationRequest;
import com.io.codetracker.application.user.command.UserProfileCommand;
import com.io.codetracker.application.user.command.UserRegistrationCommand;
import com.io.codetracker.application.user.port.in.response.FetchProfileDataResponse;
import com.io.codetracker.application.user.port.in.response.UpdateProfilePictureResponse;
import com.io.codetracker.application.user.port.in.response.UserProfileResponseDTO;
import com.io.codetracker.application.user.port.in.response.UserRegistrationResponseDTO;
import com.io.codetracker.application.user.service.ProfilePictureService;
import com.io.codetracker.application.user.port.in.response.DeleteProfilePictureResponse;
import com.io.codetracker.application.user.service.UserProfileService;
import com.io.codetracker.application.user.service.UserRegistration;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

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

    private final UserRegistration registration;
    private final UserProfileService userProfileService;
    private final ProfilePictureService updateProfilePictureService;

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> userRegistration(@AuthenticationPrincipal AuthPrincipal principal,
        @RequestPart("data") @Valid UserRegistrationRequest request,
        @RequestPart(value = "profile", required = false) MultipartFile profile) {

        UserRegistrationCommand command = new UserRegistrationCommand(request.firstName(), request.lastName(), 
        request.phoneNumber(),request.gender(),request.birthday(),profile,request.bio());

        UserRegistrationResponseDTO result = registration.completeInitialization(principal.getUserId(),command);
        return result.success() ? ResponseEntity.status(HttpStatus.OK).body(result) : ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponseDTO> updateUserProfile(
            @AuthenticationPrincipal AuthPrincipal principal,
            @Valid @RequestBody UserProfileRequest request) {
        UserProfileCommand command = new UserProfileCommand(request.firstName(), request.lastName(), request.gender(), request.phoneNumber(), request.bio(), request.birthday());
        UserProfileResponseDTO result = userProfileService.updateProfile(principal.getUserId(), command);
        return result.success() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    } 
    
    @GetMapping("/profile")
    public ResponseEntity<FetchProfileDataResponse> getProfileData(@AuthenticationPrincipal AuthPrincipal authPrincipal) {
        Optional<FetchProfileDataResponse> dataOpt = userProfileService.getProfileData(authPrincipal.getUserId());

        if (dataOpt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(dataOpt.get());
    }

    @DeleteMapping("/profile/remove")
    public ResponseEntity<?> removeProfilePicture(@AuthenticationPrincipal AuthPrincipal principal) {
          DeleteProfilePictureResponse res  = updateProfilePictureService.removeProfilePicture(principal.getUserId());
          return res.success() ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PatchMapping("/profile/update")
    public ResponseEntity<?> updateProfilePicture(@AuthenticationPrincipal AuthPrincipal principal, @RequestParam("file") MultipartFile multipartFile) {
        UpdateProfilePictureResponse response = updateProfilePictureService.updateProfilePicture(
                principal.getUserId(), multipartFile);
        return response.success() ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.message());
    }

}
