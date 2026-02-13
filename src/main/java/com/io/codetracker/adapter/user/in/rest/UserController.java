package com.io.codetracker.adapter.user.in.rest;

import com.io.codetracker.adapter.auth.out.security.AuthPrincipal;
import com.io.codetracker.adapter.user.in.dto.MeResponse;
import com.io.codetracker.application.user.command.UserProfileCommand;
import com.io.codetracker.application.user.command.UserRegistrationCommand;
import com.io.codetracker.application.user.response.UserProfileResponseDTO;
import com.io.codetracker.application.user.response.UserRegistrationResponseDTO;
import com.io.codetracker.application.user.service.UserProfileService;
import com.io.codetracker.application.user.service.UserRegistration;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserRegistration registration;
    private final UserProfileService userProfileService;

    @GetMapping("/me")
    public ResponseEntity<MeResponse> me(@AuthenticationPrincipal AuthPrincipal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new MeResponse(
            principal.getUserId(),
            principal.getUsername(),
            principal.getAuthorities()
            .stream().map(e -> e.getAuthority()).toList()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponseDTO> userRegistration(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody UserRegistrationCommand command) {
        UserRegistrationResponseDTO result = registration.completeInitialization(userDetails.getUsername(),command);
         return result.success() ? ResponseEntity.status(HttpStatus.OK).body(result) : ResponseEntity.badRequest().body(result);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileResponseDTO> updateUserProfile(
            @Valid @RequestBody UserProfileCommand command) {
        UserProfileResponseDTO result = userProfileService.execute(command);
        return result.success() ? ResponseEntity.ok(result) : ResponseEntity.badRequest().body(result);
    }    
}
