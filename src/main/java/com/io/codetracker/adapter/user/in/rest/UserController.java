package com.io.codetracker.adapter.user.in.rest;

import com.io.codetracker.application.user.command.UserRegistrationCommand;
import com.io.codetracker.application.user.response.UserRegistrationResponseDTO;
import com.io.codetracker.application.user.service.UserRegistration;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserRegistration registration;

    @PostMapping("/api/user/register")
    public ResponseEntity<UserRegistrationResponseDTO> userRegistration(@Valid @RequestBody UserRegistrationCommand command) {
        UserRegistrationResponseDTO result = registration.completeInitialization(command);
         return result.success() ? ResponseEntity.status(HttpStatus.OK).body(result) : ResponseEntity.badRequest().body(result);
    }

}
