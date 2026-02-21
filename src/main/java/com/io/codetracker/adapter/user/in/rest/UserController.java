package com.io.codetracker.adapter.user.in.rest;

import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.adapter.user.in.dto.UserRegistrationRequest;
import com.io.codetracker.application.user.command.UserProfileCommand;
import com.io.codetracker.application.user.command.UserRegistrationCommand;
import com.io.codetracker.application.user.response.FetchProfileDataResponse;
import com.io.codetracker.application.user.response.UserProfileResponseDTO;
import com.io.codetracker.application.user.response.UserRegistrationResponseDTO;
import com.io.codetracker.application.user.service.UserProfileService;
import com.io.codetracker.application.user.service.UserRegistration;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserRegistration registration;
    private final UserProfileService userProfileService;

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
            @Valid @RequestBody UserProfileCommand command) {
        UserProfileResponseDTO result = userProfileService.updateProfile(command);
        return result.success() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    } 
    
    @GetMapping("/profile")
    public ResponseEntity<FetchProfileDataResponse> getProfileData(@AuthenticationPrincipal AuthPrincipal authPrincipal) {
        Optional<FetchProfileDataResponse> dataOpt = userProfileService.getProfileData(authPrincipal.getUserId());

        if (dataOpt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(dataOpt.get());
    }
}
